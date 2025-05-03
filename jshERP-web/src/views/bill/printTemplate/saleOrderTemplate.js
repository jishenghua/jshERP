export const saleOrderTemplate = {
    "panels": [
      {
        "index": 0,
        "name": 1,
        "height": 140,
        "width": 241,
        "paperHeader": 0,
        "paperFooter": 396.85039370078744,
        "printElements": [
          {
            "options": {
              "left": 18,
              "top": 15,
              "height": 21,
              "width": 648,
              "testData": "单据表头",
              "fontSize": 21,
              "fontWeight": "700",
              "textAlign": "center",
              "hideTitle": true,
              "title": "单据表头",
              "right": 505.5,
              "bottom": 36,
              "vCenter": 262.5,
              "hCenter": 25.5,
              "field": "title",
              "coordinateSync": false,
              "widthHeightSync": false,
              "qrCodeLevel": 0
            },
            "printElementType": {
              "title": "单据表头",
              "type": "text"
            }
          },
          {
            "options": {
              "left": 109.5,
              "top": 34.5,
              "height": 12,
              "width": 465,
              "title": "店铺地址",
              "right": 573.75,
              "bottom": 45.75,
              "vCenter": 341.25,
              "hCenter": 39.75,
              "field": "companyAddress",
              "coordinateSync": false,
              "widthHeightSync": false,
              "fontSize": 12,
              "textAlign": "center",
              "textContentVerticalAlign": "middle",
              "qrCodeLevel": 0
            },
            "printElementType": {
              "title": "文本",
              "type": "text"
            }
          },
          {
            "options": {
              "left": 145.5,
              "top": 61.5,
              "height": 22.5,
              "width": 211.5,
              "title": "订单日期",
              "right": 357,
              "bottom": 72,
              "vCenter": 251.25,
              "hCenter": 60.75,
              "field": "operTimeStr",
              "coordinateSync": false,
              "widthHeightSync": false,
              "dataType": "datetime",
              "fontWeight": "700",
              "textAlign": "left",
              "textContentVerticalAlign": "middle",
              "qrCodeLevel": 0,
              "fontSize": 12
            },
            "printElementType": {
              "title": "文本",
              "type": "text"
            }
          },
          {
            "options": {
              "left": 18,
              "top": 61.5,
              "height": 24,
              "width": 129,
              "field": "name",
              "testData": "高级客户",
              "fontWeight": "700",
              "textAlign": "left",
              "textContentVerticalAlign": "middle",
              "title": "客户名称",
              "qid": "name",
              "right": 156,
              "bottom": 74.25,
              "vCenter": 91.5,
              "hCenter": 62.25,
              "coordinateSync": false,
              "widthHeightSync": false,
              "qrCodeLevel": 0,
              "fontSize": 12
            },
            "printElementType": {
              "title": "客户名称",
              "type": "text"
            }
          },
          {
            "options": {
              "left": 463.5,
              "top": 61.5,
              "height": 22.5,
              "width": 202.5,
              "field": "orderId",
              "testData": "XS888888888",
              "fontWeight": "700",
              "textAlign": "left",
              "textContentVerticalAlign": "middle",
              "title": "单据编号",
              "qid": "orderId",
              "right": 639,
              "bottom": 73.5,
              "vCenter": 551.25,
              "hCenter": 62.25,
              "coordinateSync": false,
              "widthHeightSync": false,
              "qrCodeLevel": 0,
              "fontSize": 12
            },
            "printElementType": {
              "title": "订单编号",
              "type": "text"
            }
          },
          {
            "options": {
              "left": 180,
              "top": 84,
              "height": 25.5,
              "width": 396,
              "field": "address",
              "testData": "广东省汕尾市城区 ",
              "fontWeight": "700",
              "textAlign": "left",
              "textContentVerticalAlign": "middle",
              "title": "地址",
              "qid": "tel_1",
              "right": 576,
              "bottom": 99,
              "vCenter": 378,
              "hCenter": 86.25,
              "coordinateSync": false,
              "widthHeightSync": false,
              "qrCodeLevel": 0,
              "fontSize": 12
            },
            "printElementType": {
              "title": "客户电话",
              "type": "text"
            }
          },
          {
            "options": {
              "left": 18,
              "top": 85.5,
              "height": 25.5,
              "width": 162,
              "field": "tel",
              "testData": "18888888888",
              "fontWeight": "700",
              "textAlign": "left",
              "textContentVerticalAlign": "middle",
              "title": "客户电话",
              "qid": "tel",
              "right": 156.75,
              "bottom": 81,
              "vCenter": 89.25,
              "hCenter": 68.25,
              "coordinateSync": false,
              "widthHeightSync": false,
              "qrCodeLevel": 0,
              "fontSize": 12
            },
            "printElementType": {
              "title": "客户电话",
              "type": "text"
            }
          },
          {
            "options": {
              "left": 18,
              "top": 130.5,
              "height": 145.5,
              "width": 648,
              "fields": [
                {
                  "text": "名称",
                  "field": "NAME"
                },
                {
                  "text": "数量",
                  "field": "SL"
                },
                {
                  "text": "规格",
                  "field": "GG"
                },
                {
                  "text": "条码",
                  "field": "TM"
                },
                {
                  "text": "单价",
                  "field": "DJ"
                },
                {
                  "text": "金额",
                  "field": "JE"
                },
                {
                  "text": "备注",
                  "field": "DETAIL"
                }
              ],
              "field": "table",
              "qid": "table",
              "right": 666,
              "bottom": 275.25,
              "vCenter": 342,
              "hCenter": 196.5,
              "coordinateSync": false,
              "widthHeightSync": false,
              "fontFamily": "Microsoft YaHei",
              "fontSize": 12,
              "lineHeight": 18,
              "columns": [
                [
                  {
                    "width": 81.75158921698633,
                    "title": "商品条码",
                    "field": "TM",
                    "checked": true,
                    "columnId": "TM",
                    "fixed": false,
                    "rowspan": 1,
                    "colspan": 1,
                    "align": "center",
                    "tableQRCodeLevel": 0,
                    "tableSummaryTitle": true,
                    "tableSummary": ""
                  },
                  {
                    "width": 163.2014851305022,
                    "title": "名称",
                    "field": "NAME",
                    "checked": true,
                    "columnId": "NAME",
                    "fixed": false,
                    "rowspan": 1,
                    "colspan": 1,
                    "align": "center",
                    "tableQRCodeLevel": 0,
                    "tableSummaryTitle": true,
                    "tableSummary": ""
                  },
                  {
                    "width": 159.17098513050246,
                    "title": "型号",
                    "field": "GG",
                    "checked": true,
                    "columnId": "GG",
                    "fixed": false,
                    "rowspan": 1,
                    "colspan": 1,
                    "align": "center",
                    "tableQRCodeLevel": 0,
                    "tableSummaryTitle": true,
                    "tableSummary": ""
                  },
                  {
                    "width": 44.03623513050215,
                    "title": "数量",
                    "field": "SL",
                    "checked": true,
                    "columnId": "SL",
                    "fixed": false,
                    "rowspan": 1,
                    "colspan": 1,
                    "align": "center",
                    "tableQRCodeLevel": 0,
                    "tableSummaryTitle": true,
                    "tableSummary": ""
                  },
                  {
                    "width": 66.11147846369849,
                    "title": "单价",
                    "field": "DJ",
                    "checked": true,
                    "columnId": "DJ",
                    "fixed": false,
                    "rowspan": 1,
                    "colspan": 1,
                    "align": "center"
                  },
                  {
                    "width": 72.03849179730608,
                    "title": "金额",
                    "field": "JE",
                    "checked": true,
                    "columnId": "JE",
                    "fixed": false,
                    "rowspan": 1,
                    "colspan": 1,
                    "align": "center",
                    "tableQRCodeLevel": 0,
                    "tableSummaryTitle": true,
                    "tableSummary": ""
                  },
                  {
                    "width": 61.68973513050231,
                    "title": "备注",
                    "field": "DETAIL",
                    "checked": true,
                    "columnId": "DETAIL",
                    "fixed": false,
                    "rowspan": 1,
                    "colspan": 1,
                    "align": "center"
                  }
                ]
              ]
            },
            "printElementType": {
              "title": "订单数据",
              "type": "table",
              "editable": true,
              "columnDisplayEditable": true,
              "columnDisplayIndexEditable": true,
              "columnTitleEditable": true,
              "columnResizable": true,
              "columnAlignEditable": true,
              "isEnableEditField": true,
              "isEnableContextMenu": true,
              "isEnableInsertRow": true,
              "isEnableDeleteRow": true,
              "isEnableInsertColumn": true,
              "isEnableDeleteColumn": true,
              "isEnableMergeCell": true
            }
          },
          {
            "options": {
              "left": 139.5,
              "top": 303,
              "height": 22.5,
              "width": 106.5,
              "field": "deposit",
              "testData": "100",
              "textAlign": "left",
              "textContentVerticalAlign": "middle",
              "title": "定金",
              "qid": "userName_1",
              "right": 246,
              "bottom": 323.25,
              "vCenter": 192.75,
              "hCenter": 312,
              "coordinateSync": false,
              "widthHeightSync": false,
              "qrCodeLevel": 0,
              "fontSize": 12
            },
            "printElementType": {
              "title": "客户电话",
              "type": "text"
            }
          },
          {
            "options": {
              "left": 249,
              "top": 303,
              "height": 22.5,
              "width": 123,
              "field": "discountLastMoney",
              "testData": "100",
              "textAlign": "left",
              "textContentVerticalAlign": "middle",
              "title": "折后金额",
              "qid": "deposit_1",
              "right": 351.99609375,
              "bottom": 324.99609375,
              "vCenter": 298.74609375,
              "hCenter": 313.74609375,
              "coordinateSync": false,
              "widthHeightSync": false,
              "qrCodeLevel": 0,
              "fontSize": 12
            },
            "printElementType": {
              "title": "客户电话",
              "type": "text"
            }
          },
          {
            "options": {
              "left": 370.5,
              "top": 303,
              "height": 22.5,
              "width": 135,
              "field": "otherMoney",
              "testData": "100",
              "textAlign": "left",
              "textContentVerticalAlign": "middle",
              "title": "其他费用",
              "qid": "discountLastMoney_1",
              "right": 505.5,
              "bottom": 325.5,
              "vCenter": 438,
              "hCenter": 314.25,
              "coordinateSync": false,
              "widthHeightSync": false,
              "qrCodeLevel": 0,
              "fontSize": 12
            },
            "printElementType": {
              "title": "客户电话",
              "type": "text"
            }
          },
          {
            "options": {
              "left": 507,
              "top": 303,
              "height": 22.5,
              "width": 159,
              "field": "allMoney",
              "testData": "100",
              "textAlign": "left",
              "textContentVerticalAlign": "middle",
              "title": "合计金额",
              "qid": "otherMoney_1",
              "right": 642.24609375,
              "bottom": 327.24609375,
              "vCenter": 574.74609375,
              "hCenter": 315.99609375,
              "coordinateSync": false,
              "widthHeightSync": false,
              "qrCodeLevel": 0,
              "fontSize": 12
            },
            "printElementType": {
              "title": "客户电话",
              "type": "text"
            }
          },
          {
            "options": {
              "left": 18,
              "top": 303,
              "height": 22.5,
              "width": 121.5,
              "field": "userName",
              "testData": "管理员",
              "textAlign": "left",
              "textContentVerticalAlign": "middle",
              "title": "制单人",
              "qid": "tel_1",
              "right": 666.75,
              "bottom": 325.5,
              "vCenter": 342.75,
              "hCenter": 314.25,
              "coordinateSync": false,
              "widthHeightSync": false,
              "qrCodeLevel": 0,
              "fontSize": 12
            },
            "printElementType": {
              "title": "客户电话",
              "type": "text"
            }
          },
          {
            "options": {
              "left": 18,
              "top": 325.5,
              "height": 22.5,
              "width": 648,
              "field": "remark",
              "testData": "我是备注",
              "textAlign": "left",
              "textContentVerticalAlign": "middle",
              "title": "备注",
              "qid": "remark_1",
              "right": 668.49609375,
              "bottom": 347.49609375,
              "vCenter": 344.49609375,
              "hCenter": 336.24609375,
              "coordinateSync": false,
              "widthHeightSync": false,
              "qrCodeLevel": 0,
              "fontSize": 12
            },
            "printElementType": {
              "title": "客户电话",
              "type": "text"
            }
          }
        ],
        "paperNumberLeft": 565,
        "paperNumberTop": 374,
        "paperNumberDisabled": true,
        "paperNumberContinue": true,
        "fontFamily": "Microsoft YaHei",
        "scale": 1,
        "watermarkOptions": {
          "content": "",
          "fillStyle": "rgba(184, 184, 184, 0.3)",
          "fontSize": "14px",
          "rotate": 25,
          "width": 200,
          "height": 200,
          "timestamp": false,
          "format": "YYYY-MM-DD HH:mm"
        },
        "panelLayoutOptions": {
          "layoutType": "column",
          "layoutRowGap": 0,
          "layoutColumnGap": 0
        }
      }
    ]
  }