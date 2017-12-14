<template>
	<section class="dailyTest">

	</section>
</template>

<script>


import {

} from '../../api/api';


export default {
	data() {
		return {

		}
	},
	methods: {

	},
	mounted() {


	},
	filters: {

	},
}
</script>

<style lang="scss">
// @import '~scss_index';
.dailyTest {
	background: #F1F3FB;
	.headTip {
		background: #F1F3FB;
		margin-bottom: 30px;
		margin-top: 15px;
		color: #FF4758;
		.icon_alert {
			display: inline-block;
			width: 16px;
			height: 16px;
			background: url("../../assets/img/routine_worn.png") no-repeat center center;
			vertical-align: middle;
			margin-right: 10px;
			position: relative;
			top: -1px;
		}
	}
	table {
		// thead {}
		tbody {
			.heightRisk {
				color: #f00;
			}
			.middleRisk {
				color: #FF7100;
			}
			.lowerRisk {
				color: #00BD60;
			}
			.lookDetail {
				color: #6b99fc;
				text-decoration: none;
				&.notSee {
					color: #D0D0D0;
					cursor: not-allowed;
				}
			}
			.progre {
				.pro_title {
					&.testing {
						color: #6A95FF;
					}
				}
				.el-progress {
					width: 100px;
					margin: 0 auto;
					.el-progress-bar {
						.el-progress-bar__outer {
							.el-progress-bar__inner {
								filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#6B96FF',endColorstr='#74C5FC', GradientType=1);
								filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
								background-image: -moz-linear-gradient(left, #6B96FF, #74C5FC);
								background-image: -ms-linear-gradient(left, #6B96FF, #74C5FC);
								background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#6B96FF), to(#74C5FC));
								background-image: -webkit-linear-gradient(left, #6B96FF, #74C5FC);
								background-image: -o-linear-gradient(left, #6B96FF, #74C5FC);
								background-image: linear-gradient(left, #6B96FF, #74C5FC);
							}
						}
					}
				}
			}
		}
	}
	.el-message {
		width: 275px;
		height: 110px;
		top: 50%;
		overflow: visible;
		/*.el-message__img{*/
		/*display:none;*/
		/*}*/
		.el-message__group {
			p {
				margin: 0 auto;
				padding-top: 100px;
				width: 276px;
				height: 65px;
				line-height: 16px;
				text-overflow: ellipsis;
				white-space: pre-wrap;
				text-align: center;
				word-break: break-all;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 4;
				overflow: hidden; // .el-message__icon {
				// 	display: inline-block;
				// 	width: 60px;
				// 	height: 60px;
				// 	position: absolute;
				// 	left: 50%;
				// 	top: -40px;
				// 	transform: translate(-50%);
				// 	background: url('../assets/img/popup_icon_right.png') no-repeat top left;
				// }
			}
			.el-icon-close {
				display: inline-block;
				width: 60px;
				height: 60px;
				position: absolute;
				left: 50%;
				top: -40px;
				transform: translate(-50%);
				background: url('../../assets/img/popup_icon_right.png') no-repeat top left;
				&::before {
					content: '';
				}
			}
		}
		&.error_msg {
			.el-message__group {
				// p {
				// 	.error_ico {
				// 		width: 62px;
				// 		height: 62px;
				// 		background: url('../assets/img/popup_icon_wrong.png') no-repeat top left;
				// 	}
				// }
				.el-icon-close {
					width: 62px;
					height: 62px;
					background: url('../../assets/img/popup_icon_wrong.png') no-repeat top left;
				}
			}
		}
	}
}
</style>