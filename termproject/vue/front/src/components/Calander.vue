<!-- eslint-disable -->
<template>
  <div class="calendar">
    <h2 class="text-center">
      <button @click.prevent="prevBtn(currentMonth)">◀</button>
      {{ currentYear }}년 {{ currentMonth }}월
      <button @click.prevent="nextBtn(currentMonth)">▶</button>
    </h2>
    <table class="table table-hover">
      <thead>
        <tr>
          <td class="col-sm-2" style="padding-left:15px" v-for="(weekName, i) in weekNames" :key="i">
            {{ weekName }}
          </td>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, index) in currentCalendarMatrix" :key="index">
          <td v-for="(day, index2) in row" :key="index2" :style="cal.includes(currentYear + '-' + currentMonth + '-' + day) || (currentMonth == Number(limitDate.split('-')[1]) && day == Number(limitDate.split('-')[2])) || (day != '' && currentMonth == startLimitMonth && day < startLimitDate) || (currentMonth == endLimitMonth && day > endLimitDate) ? { padding: '20px', 'background-color': 'lightgray' } : { padding: '20px' }">
            <button :style="cal.includes(currentYear + '-' + currentMonth + '-' + day) || (currentMonth == Number(limitDate.split('-')[1]) && day == Number(limitDate.split('-')[2])) ||(currentMonth == startLimitMonth && day < startLimitDate) || (currentMonth == endLimitMonth && day > endLimitDate) ? { color: 'gray' } : {}" @click.prevent="selectDate">
              {{ day }}
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
/* eslint-disable */
export default {
  name: "Calendar",
  props: {
    start_date: Date,
    end_date: Date,
    content_no: Number,
    ps_num: Number
  },
  data() {
    return {
      cal: [],

      weekNames: ["일", "월", "화", "수", "목", "금", "토"],
      rootYear: 1904,
      rootDayOfWeekIndex: 4, // 2000년 1월 1일은 토요일
      limitDate: new Date(new Date().getTime()).toISOString().slice(0, -14),

      startLimitYear: Number(this.start_date.split("-")[0]),
      startLimitMonth: Number(this.start_date.split("-")[1]),
      startLimitDate: this.start_date.split("-")[2],

      endLimitYear: Number(this.end_date.split("-")[0]),
      endLimitMonth: Number(this.end_date.split("-")[1]),
      endLimitDate: this.end_date.split("-")[2],

      currentYear: Number(this.start_date.split("-")[0]),
      currentMonth: Number(this.start_date.split("-")[1]),

      currentMonthStartWeekIndex: null,
      currentCalendarMatrix: [],
      endOfDay: null,
      selectedDate: new Date(),
      isValue: "",
      isSelected: false
    };
  },
  mounted() {
    this.calList();
    this.init();
  },
  methods: {
    calList() {
      this.startLimitYear = Number(this.start_date.split("-")[0]) >= Number(this.limitDate.split("-")[0]) ? Number(this.start_date.split("-")[0]) : Number(this.limitDate.split("-")[0]);
      this.startLimitMonth = Number(this.start_date.split("-")[1]) >= Number(this.limitDate.split("-")[1]) ? Number(this.start_date.split("-")[1]) : Number(this.limitDate.split("-")[1]);
      if(Number(this.start_date.split("-")[1]) >= Number(this.limitDate.split("-")[1])){
        this.startLimitDate = Number(this.start_date.split("-")[2]) >= Number(this.limitDate.split("-")[2]) ? this.start_date.split("-")[2] : this.limitDate.split("-")[2];
      } else{
        this.startLimitDate = Number(this.start_date.split("-")[2]) < Number(this.limitDate.split("-")[2]) ? this.start_date.split("-")[2] : this.limitDate.split("-")[2];
      }

      this.currentYear = this.startLimitYear;
      this.currentMonth = this.startLimitMonth;

      const params = new URLSearchParams();
      params.append("content_no", this.content_no);
      params.append("ps_num", this.ps_num);
      this.$axios.post("/agency/calList", params).then(res => {
        this.cal = res.data;
      });
    },
    init() {
      this.currentMonthStartWeekIndex = this.getStartWeek(this.currentYear, this.currentMonth);
      this.endOfDay = this.getEndOfDay(this.currentYear, this.currentMonth);
      this.initCalendar();
    },
    initCalendar() {
      this.currentCalendarMatrix = [];
      let day = 1;
      for (let i = 0; i < 6; i++) {
        let calendarRow = [];
        for (let j = 0; j < 7; j++) {
          if (i == 0 && j < this.currentMonthStartWeekIndex) {
            calendarRow.push("");
          } else if (day <= this.endOfDay) {
            calendarRow.push(day);
            day++;
          } else {
            calendarRow.push("");
          }
        }
        this.currentCalendarMatrix.push(calendarRow);
      }
    },
    getStartWeek(targetYear, targetMonth) {
      let year = this.rootYear;
      let month = 1;
      let sumOfDay = this.rootDayOfWeekIndex;
      while (true) {
        if (targetYear > year) {
          for (let i = 0; i < 12; i++) {
            sumOfDay += this.getEndOfDay(year, month + i);
          }
          year++;
        } else if (targetYear == year) {
          if (targetMonth > month) {
            sumOfDay += this.getEndOfDay(year, month);
            month++;
          } else if (targetMonth == month) {
            return (sumOfDay + 1) % 7;
          }
        }
      }
    },
    getEndOfDay(year, month) {
      switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
          return 31;
          break;
        case 4:
        case 6:
        case 9:
        case 11:
          return 30;
          break;
        case 2:
          if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return 29;
          } else {
            return 28;
          }
          break;
        default:
          console.log("unknown month " + month);
          return 0;
          break;
      }
    },
    prevBtn(month) {
      if (this.startLimitYear + "" + this.startLimitMonth != this.currentYear + "" + month) {
        if (this.isValue != "") {
          if (this.isValue.classList != "") {
            this.isValue.classList.toggle("rounded");
            this.isSelected = false;
            this.$emit("class_date", "");
          }
        }
        month--;
        if (month <= 0) {
          this.currentMonth = 12;
          this.currentYear -= 1;
        } else {
          this.currentMonth -= 1;
        }
        this.init();
      }
    },
    nextBtn(month) {
      if (this.endLimitYear + "" + this.endLimitMonth != this.currentYear + "" + month) {
        if (this.isValue != "") {
          if (this.isValue.classList != "") {
            this.isValue.classList.toggle("rounded");
            this.isSelected = false;
            this.$emit("class_date", "");
          }
        }
        month++;
        if (month > 12) {
          this.currentMonth = 1;
          this.currentYear += 1;
        } else {
          this.currentMonth += 1;
        }
        this.init();
      }
    },
    selectDate(event) {
      var day = Number(event.currentTarget.textContent);
      this.selectedDate = new Date();
      this.selectedDate.setFullYear(this.currentYear);
      this.selectedDate.setMonth(this.currentMonth - 1);
      this.selectedDate.setDate(day);
      if (!(this.cal.includes(this.currentYear + "-" + this.currentMonth + "-" + day) || (this.currentMonth == Number(this.limitDate.split("-")[1]) && day == Number(this.limitDate.split("-")[2])) ||(this.currentMonth == this.startLimitMonth && day < this.startLimitDate) || (this.currentMonth == this.endLimitMonth && day > this.endLimitDate))) {
        if (event.currentTarget.classList.length) {
          this.$emit("class_date", "");
          this.isSelected = false;
        } else {
          if (this.isSelected) {
            this.isValue.classList.toggle("rounded");
            this.$emit("class_date", this.selectedDate.toISOString().slice(0, -14));
            this.isValue = event.currentTarget;
          } else {
            this.$emit("class_date", this.selectedDate.toISOString().slice(0, -14));
            this.isSelected = true;
            this.isValue = event.currentTarget;
          }
        }
        event.currentTarget.classList.toggle("rounded");
      }
    }
  }
};
</script>

<style type="text/css">
.rounded {
  font-weight: bold;
  color: red;
  font-size: 15px;
}
</style>
