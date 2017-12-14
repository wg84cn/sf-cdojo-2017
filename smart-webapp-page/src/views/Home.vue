<template>
  <el-row class="container systemList">

    <el-col :span="24" class="header">

      <div class="header_contian">
        <el-col :span="5" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
          <i class="logoName"></i> {{collapsed?'':sysName}}
        </el-col>
        <el-col :span="9" class="headList2">
          <!--导航菜单-->
          <el-menu :default-active="$route.path" class="el-menu-vertical-demo" @open="handleopen" @close="handleclose" @select="handleselect" unique-opened router v-show="!collapsed">
            <template v-for="(item,index) in $router.options.routes" v-if="!item.hidden">
              <el-submenu :index="index+''" v-if="!item.leaf">
                <template slot="title">
                  <i :class="item.iconCls"></i>{{item.name}}</template>
                <el-menu-item v-for="child in item.children" :index="child.path" :key="child.path" v-if="!child.hidden">{{child.name}}</el-menu-item>
              </el-submenu>

              <el-menu-item v-if="item.leaf&&item.children.length>0" :index="item.children[0].path">
                <i :class="item.iconCls"></i>{{item.children[0].name}}</el-menu-item>

            </template>
          </el-menu>

        </el-col>
        <el-col :span="10" class="userinfo">

          <div class="advice" @click="adviceShow">
            <img src="../assets/img2/nav_opinion.png" alt="意见反馈"> 意见反馈
          </div>

          <el-dropdown trigger="hover">

            <span class="el-dropdown-link userinfo-inner">
              {{personDetail.lastName}}
              <i class="el-icon-caret-bottom el-icon--right"></i>
            </span>

            <el-dropdown-menu slot="dropdown" class="myDropDown">
              <el-dropdown-item @click.native="personDetailVisible=true">
                <i class="personDetial"></i>个人资料</el-dropdown-item>
              <el-dropdown-item @click.native="dowloadBook">
                <i class="useBook"></i>用户手册</el-dropdown-item>
              <el-dropdown-item @click.native="logout">
                <i class="logOut"></i>退出</el-dropdown-item>
            </el-dropdown-menu>

          </el-dropdown>
        </el-col>
      </div>

    </el-col>

    <el-col :span="24" class="handle_tip" v-show="firstLogin">

      <img class="tip_back" :src="tipImg" alt="">
      
      <img class="prev_step" :style="{'left':prevLeft,'top':prevTop}" @click="prevStep" v-show="currentStep!=0" src="../assets/img2/prevStep.svg" alt="上一步">

      <img class="i_konw" :style="{'left':prevLeft,'top':prevTop}" @click="nextStep" src="../assets/img2/iKow.svg" alt="下一步">

    </el-col>

    <!--个人资料界面-->
    <el-dialog title="个人资料" top="30%" v-model="personDetailVisible" :close-on-click-modal="false" class="fd_modal person_detail">

      <el-form :model="personDetail" label-width="130px">

        <el-form-item label="姓名: " prop="center">
          <el-input v-model="personDetail.lastName" auto-complete="off" readonly="readonly" class="no_border"></el-input>
        </el-form-item>

        <el-form-item label="工号: " prop="center">
          <el-input v-model="personDetail.empNum" auto-complete="off" readonly="readonly" class="no_border"></el-input>
        </el-form-item>
        
        <el-form-item label="邮箱: " prop="center">
          <el-input v-model="personDetail.mailAddress" auto-complete="off" readonly="readonly" class="no_border"></el-input>
        </el-form-item>

      </el-form>

    </el-dialog>

    <transition name="modal_show">
      
      <!--意见反馈-->
      <el-dialog v-if="adviceVisible" title="意见反馈" top="30%" v-model="adviceVisible" :close-on-click-modal="false" class="fd_modal person_detail">

        <el-form :model="adviceDetail" label-width="0" ref="adviceId">

          <el-form-item class="no_radius" :prop="'opinion'" :rules="[{required: true, message: '请填写意见反馈', trigger: 'blur'},{min:20,message:'不能少于20字'},{max:200,message:'不能超过200字'}]">
            <!-- :maxlength="200" :minlength="20" -->
            <el-input :rows="8" type="textarea" class="advice_detail" v-model="adviceDetail.opinion" autofocus placeholder="请填写您的意见或建议（20字到200字）"></el-input>

          </el-form-item>

        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button type="primary" :disabled="adviceDetail.opinion.length<20 || adviceDetail.opinion.length>200" @click.native="subAdvice" class="btn_blue">确定</el-button>
        </div>

      </el-dialog>

    </transition>

    <el-col :span="24" class="main">
      <section class="content-container">
        <div class="grid-content bg-purple-light">
          <el-col :span="24" class="content-wrapper">
            <transition name="fade" mode="out-in">
              <router-view></router-view>
            </transition>
          </el-col>
        </div>
      </section>
    </el-col>

  </el-row>
</template>

<script>

// import {
//   mapGetters,
//   mapActions
// } from 'vuex';

import {
  sapPersonInfo,
  downloadManual,
  getDictionaryList,
  listAdvice,
  isFirstLogin
} from '../api/api';

export default {
  data() {
    return {
      sysName: '丰洞安全检测平台',
      collapsed: false,
      currentTime: '',

      // 查看个人信息界面
      personDetailVisible: false,
      personDetail: {
        lastName: '',
        empNum: '',
        mailAddress: ''
      },

      //意见反馈
      adviceVisible: false,
      adviceDetail: {
        opinion: ''
      },

      // 全局字典数据
      serverTypeOptions: [],
      dbTypeOptions: [],
      osTypeOptions: [],

      currentStep:0,
      tipImgAll:[
        'static/handleGuild/step1.png',
        'static/handleGuild/step2.png',
        'static/handleGuild/step3.png',
        'static/handleGuild/step4.png',
        'static/handleGuild/step5.png',
      ],
      tipImg:'',
      firstLogin:false,
      prevLeft:'40%',
      prevTop:'40%',
      production_path:'/',
      // myDiction:this.$store.state.app.diction
    }
  },
  methods: {
    // ...mapActions([
    //   'increment', // 映射 this.increment() 为 this.$store.dispatch('increment')
    //   'decrement'
    // ]),
    prevStep(){
      this.currentStep --;
      //  console.log('step',this.currentStep,this.tipImgAll.length);
      if(this.currentStep<0){
        return
      }
      if(this.currentStep == 0){
        this.prevLeft = '40%';
        this.prevTop = '40%';
      }
      if(this.currentStep == 1){
        this.prevLeft = '40%';
        this.prevTop = '50%';
      }
      if(this.currentStep == 2){
        this.prevLeft = '40%';
        this.prevTop = '60%';
      }
      if(this.currentStep == 3 || this.currentStep == 4){
        this.prevLeft = '50%';
        this.prevTop = '30%';
      }
      this.tipImg = this.production_path+this.tipImgAll[this.currentStep]
    },
    nextStep(){
      this.currentStep ++;
      // console.log('step',this.currentStep,this.tipImgAll.length);
      if(this.currentStep>=this.tipImgAll.length){
        this.firstLogin = false;
        return
      }
      if(this.currentStep == 0){
        this.prevLeft = '40%';
        this.prevTop = '40%';
      }
      if(this.currentStep == 1){
        this.prevLeft = '40%';
        this.prevTop = '50%';
      }
      if(this.currentStep == 2){
        this.prevLeft = '40%';
        this.prevTop = '60%';
      }
      if(this.currentStep == 3 || this.currentStep == 4){
        this.prevLeft = '50%';
        this.prevTop = '30%';
      }

      this.tipImg = this.production_path+this.tipImgAll[this.currentStep]
    },
    adviceShow(){
      this.adviceVisible=true;
      this.$nextTick(function() {
        this.$refs.adviceId.resetFields();
      });
    },
    subAdvice() {

      var that = this;

      this.$refs.adviceId.validate((valid) => {

        if (valid) {

          let params2 = new URLSearchParams();
          params2.append('adviceInfo.advicerName',this.personDetail.lastName);
          params2.append('adviceInfo.advicerCode',this.personDetail.empNum);
          params2.append('adviceInfo.adviceInfo',this.adviceDetail.opinion);

          listAdvice(params2).then((res) => {
              // console.log('res',res);
              
              if(res && res.success){
                this.$message({
                  message: '意见反馈成功',
                  type: 'success',
                  showClose: true,
                  customClass: 'success_msg',
                  iconClass: 'success_ico',
                  duration: '1000',
                  onClose: function() {
                    that.adviceVisible = false;
                    that.adviceDetail.opinion = '';
                  }
                });
              }else{
               	this.$message({
                  message: res.msg,
                  type: 'error',
                  showClose: true,
                  customClass: 'error_msg',
                  iconClass: 'error_ico',
                  duration: '2000',
                });

              }
          });

        
        }

      })

    },
    onSubmit() {
      console.log('submit!');
    },
    handleopen() {
      //console.log('handleopen');
    },
    handleclose() {
      //console.log('handleclose');
    },
    handleselect: function(a, b) {

    },
    dowloadBook() {
      window.open(downloadManual);
      // location.href=downloadManual;
    },
    //退出登录
    logout: function() {

      window.location.href = '/logout.pub';

      sessionStorage.removeItem('user');

      //              this.$router.push('/login');

      //				var _this = this;
      //				this.$confirm('确认退出吗?', '提示', {
      //					//type: 'warning'
      //				}).then(() => {
      //					sessionStorage.removeItem('user');
      //					_this.$router.push('/login');
      //				}).catch(() => {
      //
      //				});

    },
    //折叠导航栏
    collapse: function() {
      this.collapsed = !this.collapsed;
    },
    initData() {

      isFirstLogin().then((res) => {
        if(res && res.data){
          this.tipImg = this.production_path+this.tipImgAll[0];
        
          this.firstLogin = res.data.isFirstLogin

        }
        // console.log('isFirstLogin',res);
      });

      sapPersonInfo().then((res) => {
        if (res.status == 200 && res.statusText == 'OK') {
          this.personDetail = res.data;
        } else {
          console.log('error', res.msg);
          // this.$message.error(res.msg);
        }
      });

    },
    getDiction() {
      // let params2 = new URLSearchParams();
      // params2.append('limit', 1000);
      // params2.append('start', 0);

      getDictionaryList({
        'limit': 1000,
        'start': 0
      }).then((res) => {

        if (res.data) {
          var dicArr = res.data.data
          this.serverTypeOptions = []
          this.dbTypeOptions = []
          this.osTypeOptions = []

          dicArr.forEach((val, ind) => {
            if (val.pkeyPath == 'ASATSetting.ServerType' && val.dicKey!='ServerType') {
              this.serverTypeOptions.push({
                value: val.dicValue,
                label: val.dicName
              });
            }
            if (val.pkeyPath == 'ASATSetting.DBType') {
              this.dbTypeOptions.push({
                value: val.dicValue,
                label: val.dicName
              });
            }
            if (val.pkeyPath == 'ASATSetting.OSType') {
              this.osTypeOptions.push({
                value: val.dicValue,
                label: val.dicName
              });
            }
            if (val.dicKey == 'TestVersions') {
               this.$store.commit('TESTVERSIONS', val.dicValue);
            }
            if (val.dicKey == 'OnlineDays') {
               this.$store.commit('ONLINEDAYS', val.dicValue);
            }

          });

          this.$store.commit('DBTYPE', this.dbTypeOptions);
          this.$store.commit('SERVERTYPE', this.serverTypeOptions);

          this.$store.commit('OSTYPE', this.osTypeOptions);

          // console.log('store',this.$store);

          // this.$store.dispatch('decrement', 123).then(()=>{
          //     console.log('isDecrement');
          // });

        } else {
          console.log('获取数据字典失败');
        }
      })
    },

  },
  computed: {
    // 使用对象展开运算符将 getters 混入 computed 对象中
    // ...mapGetters([
    //   'diction'
    // ])
  },
  mounted() {
    this.initData();
    this.getDiction();

    if (process.env.NODE_ENV === 'development'){
      this.production_path = '/'
    }else{
      this.production_path = '/pc-rs/asat.portal.common/' //打包时用这个
    }

  }
}
</script>

<style scoped lang="scss">
// @import '~scss_index';

.myDropDown.el-dropdown-menu {
  &::before {
    content: '';
    display: inline-block;
    position: absolute;
    right: 40px;
    top: -9px;
    width: 0;
    height: 0;
    border-left: 10px solid transparent;
    border-right: 10px solid transparent;
    border-bottom: 10px solid #fff;
  }
  .el-dropdown-menu__item {
    width: 210px;
    padding: 0 22px;
    box-sizing: border-box;
    i {
      display: inline-block;
      width: 16px;
      height: 16px;
      vertical-align: middle;
      margin-right: 10px;
      position: relative;
      top: -2px;
      &.personDetial {
        background: url("../assets/img/shezhi.png") no-repeat center center;
      }
      &.useBook {
        background: url("../assets/img/shouce.png") no-repeat center center;
      }
      &.logOut {
        background: url("../assets/img/tuichu.png") no-repeat center center;
      }
    }
    &+.el-dropdown-menu__item {
      margin-top: 10px;
    }
  }
}

.systemList {
  position: absolute;
  top: 0;
  bottom: 0;
  width: 100%;
  .person_detail {
    .el-dialog {
      margin-bottom: 0;
      .el-form {
        .el-form-item {
          margin-bottom: 5px;
        }
      }
    }
  }
  .el-menu-vertical-demo {
    background-color: #fff;
    .el-menu-item {
      float: left;
    }
  }
  .handle_tip{
    position: fixed;
    width: 100%;
    height: 100%;
    // background: red;
    z-index: 1000000;
    .tip_back{
      position: absolute;
      width: 100%;
      height: 100%;
    } 
    .prev_step{
      position: absolute;
      // left: 50%;
      // top: 50%;
      cursor: pointer;
    }
    .i_konw{
      position: absolute;
      // left: 50%;
      // top: 50%;
      margin-left: 220px;
      cursor: pointer;
    }
  }
  .header {
    position: fixed;
    z-index: 1000;
    height: 80px;
    line-height: 80px;
    background: #fff; // color: #fff;
    box-shadow: 0 0 10px rgba(147, 161, 185, 0.6);
    .header_contian {
      height: 80px;
      width: 93%;
      margin: 0 auto;
      .logo {
        height: 80px;
        font-size: 26px;
        width: 260px;
        margin-right: 50px;
        color: #6b99fc;
        i {
          display: inline-block;
          float: left;
          width: 46px;
          height: 38px;
          vertical-align: middle;
          position: relative;
          top: 20px;
          background: url("../assets/img/ht-logo.png") no-repeat center center;
        }
        .txt {
          color: #fff;
        }
      }
      .headList2 {
        width: 380px;
        .el-menu {

          height: 100%;
          .el-submenu__title,
          .el-menu-item {
            height: 80px;
            line-height: 80px;
            color: #6b99fc;
            font-size: 14px;
            &:hover {
              background-color: #6b99fc;
              color: #fff;
            }
          }
          .el-menu-item {
            width: 100px;
            text-align: center;
            padding: 0;
            padding-left: 0!important;
            padding-top: 20px;
            margin-right: 40px;
            &:last-child {
              margin-right: 0;
            }
            &.is-active {
              color: #fff;
              background: #6b99fc;
              .icon-home {
                background: url("../assets/img/home_list_sel.png") no-repeat;
              }
              .icon-daily {
                background: url("../assets/img/home_test_sel.png") no-repeat;
              }
              .icon-onLine {
                background: url("../assets/img/home_testresult_sel.png") no-repeat;
              }
            }
            &:hover {
              .icon-home {
                background: url("../assets/img/home_list_sel.png") no-repeat;
              }
              .icon-daily {
                background: url("../assets/img/home_test_sel.png") no-repeat;
              }
              .icon-onLine {
                background: url("../assets/img/home_testresult_sel.png") no-repeat;
              }
            }
            i {
              display: inline-block;
              width: 29px;
              height: 30px;
              position: absolute;
              left: 50%;
              top: 50%;
              transform: translate(-50%, -90%);
            }
            .icon-home {
              width: 28px;
              height: 24px;
              background: url("../assets/img/home_list_nor.png") no-repeat;
            }
            .icon-daily {
              background: url("../assets/img/home_test_nor.png") no-repeat;
            }
            .icon-onLine {
              background: url("../assets/img/home_testresult_nor.png") no-repeat;
            }
          }
        }
      }
      .userinfo {
        width: calc(100% - 700px);
        text-align: right;
        float: right;
        .advice {
          display: inline-block;
          width: 100px;
          height: 100%;
          margin-right: 20px;
          color: #8fa2b2;
          cursor: pointer;
          img {
            margin-right: 10px;
            vertical-align: middle;
          }
        }
        .currentTime {
          display: inline-block;
          color: #B5C1CB;
          height: 80px;
          margin-right: 30px;
          font-size: 12px;
        }
        .myHeaderContain {
          display: inline-block;
          width: 45px;
          height: 45px;
          vertical-align: bottom;
          position: relative;
          top: -20px;
          margin-right: 6px;
        }
        .el-dropdown {
          .userinfo-inner {
            cursor: pointer;
            color: #8fa2b2;
            display: inline-block;
            height: 80px;
          }
        }
      }
      .logo-collapse-width {
        width: 60px
      }
    }
  }
  .main {
    display: flex;
    background: #F1F3FB;
    position: absolute;
    top: 80px;
    bottom: 0;
    .content-container {
      flex: 1;
      padding: 20px 0;
      .grid-content {
        min-height: 600px;
        width: 93%;
        margin: 0 auto;
        .content-wrapper {
          background-color: #F1F3FB;
          box-sizing: border-box;
        }
      }
    }
  }
}
</style>