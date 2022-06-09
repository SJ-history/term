import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import login from '@/components/login'
import Join from '@/components/Join'
import Layout from '@/components/Layout'
import ClassList from '@/components/ClassList'
import EndClass from '@/components/EndClass'
import MyPage from '@/components/MyPage'
import FindPass from '@/components/FindPass'
import FindId from '@/components/FindId'
import Withdraw from '@/components/Withdraw'
import ArtistChange from '@/components/ArtistChange'
import EmailAccess from '@/components/EmailAccess'
import WriteClass from '@/components/WriteClass'
import ClassDetail from '@/components/ClassDetail'
import ClassManageApply from '@/components/ClassManageApply'
import Search from '@/components/Search'
import ClassReservationList from '@/components/ClassReservationList'

/* eslint-disable */
Vue.use(Router)

export default new Router({
        routes: [{
            path: '/',
            component: Layout,
            children: [{
                    path: '/',
                    component: Home,
                    name: 'Home',

                },
                {
                    path: '/login',
                    component: login,
                    name: 'login'
                }, {
                    path: '/join',
                    component: Join,
                    name: 'Join'
                }, {
                    path: '/classList',
                    component: ClassList,
                    name: 'ClassList'
                }, {
                    path: '/endClass',
                    component: EndClass,
                    name: 'EndClass'
                }, {
                    path: '/myPage',
                    component: MyPage,
                    name: 'MyPage'
                }, {
                    path: '/artistChange',
                    component: ArtistChange,
                    name: 'ArtistChange'
                }, {
                    path: '/findPass',
                    component: FindPass,
                    name: 'FindPass'
                }, {
                    path: '/findId',
                    component: FindId,
                    name: 'FindId'
                }, {
                    path: '/withdraw',
                    component: Withdraw,
                    name: 'Withdraw'
                }, {
                    path: '/emailAccess',
                    component: EmailAccess,
                    name: 'EmailAccess'
                }, {
                    path: '/writeClass',
                    component: WriteClass,
                    name: 'WriteClass'
                }, {
                    path: '/classDetail',
                    component: ClassDetail,
                    name: 'ClassDetail'
                }, {
                    path: '/classManageApply',
                    component: ClassManageApply,
                    name: 'ClassManageApply'
                }, {
                    path: '/search',
                    component: Search,
                    name: 'Search'
                }, {
                    path: '/classReservationList',
                    component: ClassReservationList,
                    name: 'ClassReservationList'
                },
            ]
        }],
        mode: 'history'
    },

)