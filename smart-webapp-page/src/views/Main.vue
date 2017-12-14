<template>
	<section class="subSystemList">

	</section>
</template>

<script>

import {

} from '@/api/api';

export default {
	components: { },
	data() {

		return {

		}
		
	},

	computed: {

	},
    methods: {

    },
	mounted() {

	},
	filters: {
		timeFormatter: function(time) {
			if (!time) {
				return "未测试"
			}
			return time.split("T").join(" ");
		},
	},
}
</script>

<style  rel="stylesheet/scss" lang="scss">

@mixin modelSpan($color) {
	position: relative;
	color: $color;
	font-size: 13px;
	margin-left:10px;
	&::before {
		content: '';
		display: inline-block;
		width: 8px;
		height: 8px;
		border-radius: 50%;
		background: $color;
		position: absolute;
		left: -20px;
		top: 50%;
		margin-top: -4px;
		// margin-left: -30px;
	}
}

.sub_site {
	@include modelSpan(#F99803) // color:#F99803;
}

.sub_APP {
	@include modelSpan(#8EA2B3);
	// left: 13px;
}

.sub_scription {
	@include modelSpan(#8986FA);
	// left: 7px;
}

.sub_service {
	@include modelSpan(#666666);
	// left: 23px;
}

.sub_sys {
	@include modelSpan(#6A95FF);
}

.handleDrop.el-dropdown-menu {

	&[x-placement="top-end"] {
		&::before {
			top: 100%;
			border-top: 10px solid #fff;
			border-bottom: none;
		}
	}

	&::before {
		content: '';
		display: inline-block;
		position: absolute;
		right: 46px;
		top: -9px;
		width: 0;
		height: 0;
		border-left: 10px solid transparent;
		border-right: 10px solid transparent;
		border-bottom: 10px solid #fff;
	}
	.el-dropdown-menu__item {
		line-height: 31px;
		padding-left: 15px;
		color: #83A6FF;

		span,
		a {
			position: relative;
			transition: all .3s;
			z-index: 1;

			display: inline-block;

			&:hover::before {
				transform: scaleX(1);
			}
			&::before {
				position: absolute;
				transition: all .3s;
				content: "";
				display: block;
				background: #6A95FF;
				z-index: -1;
				height: 2px;
				width: 100%;
				transform: scaleX(0);
				bottom: 14px;
				left: 0;
				right: 0;
				margin: auto;
			}
		}

		&:hover,
		&.active {
			background-color: transparent;
		}
	}
}

.subSystemList {

	.add_dir {
		position: absolute;
		right: -20px;
		top: 10px;
		cursor: pointer;
		&.disAdd {
			color: #eee;
			cursor: not-allowed;
		}
	}

	.el-dialog {
		min-width: 630px;
	}

	.cookieBack {
		width: 270px;
		height: 110px;
		background: #fff;
		box-shadow: 0 0 10px #ddd;
		position: fixed;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);
		z-index: 5000;
		/*margin-left: -135px;
            margin-top: -55px;*/
		.el-alert__content {
			text-align: center;
			.el-alert__title {
				font-size: 16px;
				color: #000;
				display: inline-block;
				margin-top: 38px;
			}
		}
	}

	.fd_modal {

		.el-steps {
			padding: 20px 0;
			padding-left: 45px;
			.el-step {
				.el-step__head {
					width: 10px;
					height: 10px;
					line-height: 10px;
					background-color: #E3E6E8;
					border-color: #E3E6E8;
					&.is-text.is-success {
						background-color: #6b99fc;
						border-color: #6b99fc;
					}
					.el-step__line {

						background-color: #E3E6E8;
						top: 6px;
						left: 6px;
					}
					.el-step__icon {
						display: none;
					}
				}

				.el-step__main {
					.el-step__title.is-success {
						color: #557EFF;
					}
				}
			}
		}

		.el-textarea {
			.el-textarea__inner {
				border-radius: 0;
				min-height: 70px;
			}
		}

		.el-radio-group.myRadioGroup {
			.el-radio-button {
				.el-radio-button__inner {
					border-radius: 0;
					width: 67px;
					height: 37px;
					line-height: 15px;
				}
				.el-radio-button__orig-radio:checked+.el-radio-button__inner {
					background-color: transparent;
					color: #6b99fc;
					border-color: #6b99fc;
				}
				&.is-active,
				.el-radio-button__orig-radio:checked+.el-radio-button__inner {
					.el-radio-button__inner {
						background-color: transparent;
						color: #6b99fc;
						border-color: #6b99fc;
					}
					&::before {
						content: '';
						display: inline-block;
						position: absolute;
						right: 0;
						top: 0;
						width: 0;
						height: 0;
						border-top: 22px solid #6b99fc;
						border-left: 22px solid transparent;
					}
				}
			}
		}

		&.cookieModal {

			.inputHit {
				margin-bottom: 30px;
				font-size: 16px;
			}
			.el-textarea__inner {
				min-height: 190px;
				border-radius: 0;
			} // .el-form-item{
			// 	margin-bottom: 0px;
			// }
		}

		&.scanModal {
			.el-dialog--large {
				width: 60%;
				max-width: 900px;
				min-width: 750px;
			}
			.el-checkbox-group {
				margin-bottom: 20px;
				div+div {
					margin-top: 20px;
				}
			}
			.el-form-item {
				.el-input-number {
					vertical-align: bottom;
					margin-right: 10px;
				}
			}
		}

		&.onLineModal{
	
			.online_part{
				&+.online_part{
					padding-top: 10px;
					border-top: 1px solid #CFD0DE;
				}
				.onine_part_head{
				  height: 30px;
					line-height: 30px;
					margin-left: 20px;
				}
			}
		}
	}
	
	.headTip {
		margin-bottom: 30px;
    margin-left: 20px;
		color: #FF4758;
		text-align: justify;
		font-size: 12px;
    line-height: 18px;
		.icon_alert {
			display: inline-block;
			width: 16px;
			height: 16px;
			background: url("../assets/img/routine_worn.png") no-repeat center center;
			vertical-align: middle;
			margin-right: 10px;
			position: relative;
			top: -1px;
		}
	}

	table {
		thead {
			.chosenList {
				text-align: center;
			}
		}
		tbody {
			.progre {
				position: relative;
				left: 56px;
				.pro_title {
					color: #00BD60;
				}
				.el-progress {
					width: 100px;
					margin: 0 auto;
					margin-top: 6px;
					.el-progress-bar {
						.el-progress-bar__outer {
							.el-progress-bar__inner {
								background-color: #00BD60;
							}
						}
					}
				}
			}
			span.el-tag {
				cursor: pointer;
				border: 1px solid #d4e9ff;
				border-radius: 0;
				width: 80px;
				height: 30px;
				background: #d4e9ff;
				color: #6b99fc;
				line-height: 28px;
				text-align: center;
				font-size: 13px;
				&:hover {
					border: 1px solid #6b99fc;
					background: #6b99fc;
					color: #fff;
				}
				&.el-tag--gray {
					border: 1px solid #feead1;
					background: #feead1;
					color: #f69727;
					&:hover {
						border: 1px solid #f69727;
						background: #f69727;
						color: #fff;
					}
				}
			}
			.el-dropdown {
				width: 100%;
				.el-dropdown-link {
					display: inline-block;
					width: 100%;
					// img {
					// 	position: relative;
					// 	top: 5px;
					// }
				}
			}
			.chosenList {
				.cell {
					width: 100%;
					height: 100%;
					.radioContian {
						width: 100%;
						height: 100%;
						text-align: center;
						.el-radio {
							padding-top: 16px;
							box-sizing: border-box;
							width: 100%;
							height: 100%;
							.el-radio__label {
								display: none;
							}
						}
					}
				}
			}
			.lookDetail {
				color: #6b99fc;
				text-decoration: none;
			}
			.el-table__expand-icon {
				text-align: right;
				.el-icon {
					// display: none; //
					display: inline-block;
					width: 20px;
					height: 20px;
					background: url("../assets/img2/home_down.png") no-repeat center center;
					position: relative;
					top: 12px;
					left: 4px;
					&::before {
						content: ''
					}
				}
				&.el-table__expand-icon--expanded {
					transform: none;
					.el-icon {
						background: url("../assets/img2/home_up.png") no-repeat center center;
					}
				}
			}
			td.el-table__expanded-cell {
				border: none!important;
				box-shadow: none;
				padding: 0;
				.el-table{
					&::before{
						display:none;
					}
					&::after{
						display:none;
					}
				}
				.sub_table{
					border: none;
					.el-table__header-wrapper{
						display:none;
					}
					tr{
						background: #F4FAFF;
					}
					.el-table__body-wrapper{
						table{
							width: 100%!important;
							overflow:hidden;
						}
					}
				}

			}
		}
	}

	.el-table .el-table__body {

		tr:not(.empty-row) {
			>td:nth-last-child(2),
			>td:nth-last-child(1) {
				z-index: 100;
			}
		}

		.empty-row {
			.el-table__expand-column {
				.cell {
					display: none;
				}
			}
			.el-tag--success {
				display: none;
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
			background: url('../assets/img/popup_icon_right.png') no-repeat top left;
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
				background: url('../assets/img/popup_icon_wrong.png') no-repeat top left;
			}
		}
	}
}
</style>