<template>

	<div class="loginBg">
		<div class="sysName">
			<i class="logoName"></i> 丰洞
		</div>
		<el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px" class="demo-ruleForm login-container">
			<h3 class="title">用户登录</h3>
			<el-form-item prop="">
				<img src="../assets/img/yonghu.png" class="p1">
				<el-input type="text" v-model="ruleForm2.username" auto-complete="off" placeholder="账号"></el-input>
			</el-form-item>
			<el-form-item prop="">
				<img src="../assets/img/mima.png" class="p1">
				<el-input type="password" v-model="ruleForm2.password" auto-complete="off" placeholder="密码"></el-input>
			</el-form-item>

			<el-form-item prop="" class="checkCode">
				<el-input type="text" v-model="ruleForm2.code" auto-complete="off" placeholder="验证码" style="width: 150px;" :maxlength="4"></el-input>
			</el-form-item>

			<el-form-item style="width:100%;margin-bottom:0;">
				<el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2">登录</el-button>
			</el-form-item>
		</el-form>
	</div>

</template>

<script>
	import {
		login
	} from '../api/api';
	//import NProgress from 'nprogress'
	import qs from 'qs';
	export default {
		data() {
			return {
				logining: false,
				ruleForm2: {
					username: '618721',
					password: '123123',
					code: '2536'
				},
				rules2: {
					account: [{
							required: true,
							message: '请输入账号',
							trigger: 'blur'
						}
						//{ validator: validaePass }
					],
					checkPass: [{
							required: true,
							message: '请输入密码',
							trigger: 'blur'
						}
						//{ validator: validaePass2 }
					],
					checkCode: [{
						required: false,
						message: '请输入验证码',
						trigger: 'blur'
					}, ],
				}
			};
		},
		methods: {
			handleReset2() {
				this.$refs.ruleForm2.resetFields();
			},
			handleSubmit2(ev) {
				this.$refs.ruleForm2.validate((valid) => {
					if (valid) {

						this.logining = true;

						sessionStorage.setItem('user',
							'{"id":1,"username":"admin","avatar":"https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png","name":"张某某"}'
						);
						this.$router.push({
							path: '/main'
						});

						//              var loginParams = { username: this.ruleForm2.username, password: this.ruleForm2.password };
						//              var params = new URLSearchParams();
						//              params.append('username', this.ruleForm2.username);
						//              params.append('password', this.ruleForm2.password);
						//              params.append('verifyCode', this.ruleForm2.code);
						//
						//              requestLogin(loginParams).then(data => {
						//                  console.log(data);
						//                  this.logining = false;
						//                  //NProgress.done();
						//                  let { msg, code, user } = data;
						//                  if (code !== 200) {
						//                    this.$message({
						//                      message: msg,
						//                      type: 'error'
						//                    });
						//              } else {
						//                sessionStorage.setItem('user', JSON.stringify(user));
						//                this.$router.push({ path: '/main' });
						//              }
						//            });


					} else {
						console.log('error submit!!');
						return false;
					}
				});
			}
		}
	}
</script>

<style lang="scss">

	.loginBg {
		// background: url("../assets/img/login-bj.jpg") no-repeat center center;
		width: 100%;
		height: 100%;
		.sysName {
			font-size: 44px;
			color: #fff;
			position: absolute;
			left: 12.7%;
			top: 7.9%;
			.logoName {
				display: inline-block;
				width: 54px;
				height: 44px;
				vertical-align: middle;
				position: relative;
				top: -3px;
				background: url("../assets/img/denglu-ico.png") no-repeat center center;
			}
		}
		.login-container {
			-webkit-border-radius: 5px;
			border-radius: 5px;
			-moz-border-radius: 5px;
			background-clip: padding-box;
			width: 300px;
			padding: 35px 45px;
			background: transparent;
			border: 2px solid #0085CA;
			box-shadow: 0 0 15px #11325A inset;
			position: absolute;
			right: 16%;
			top: 29%;
			.title {
				margin: 0 auto 30px auto;
				text-align: center;
				color: #fff;
				font-size: 22px;
				font-weight: normal;
				letter-spacing: 3px;
			}

			.el-form-item {
				.p1 {
					position: absolute;
					left: 15px;
					top: 12px;
					z-index: 2;
				}
				input {
					text-align: left;
					border: 1px solid #046FA3;
					border-radius: 0;
					padding-left: 45px;
					background-color: transparent;
					color: #fff;
					height: 50px;
					line-height: 40px;
				}
				button {
					height: 50px;
					font-size: 22px;
					letter-spacing: 10px;
					background-color: #0070FF;
					border: 1px solid #5BCBFF;
				}
			}
			.el-form-item.checkCode input {
				padding-left: 12px;
			}
		}
	}
	
</style>