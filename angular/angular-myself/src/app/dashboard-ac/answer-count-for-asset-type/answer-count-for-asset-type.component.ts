import { Component, OnInit, Input } from '@angular/core';
//, AfterViewInit, OnChanges, SimpleChanges ,DoCheck
import { Inject, ViewChild } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
//underscore@1.8.3
import * as _ from 'underscore';
//wijmo
//import { WjAutoComplete } from 'wijmo/wijmo.angular2.input';
import * as wjNg2Input from 'wijmo/wijmo.angular2.input';

//dashboard
import { scoresForAsset, WidgetModel } from '../model/widget-model';
import { DashboardService } from '../share/dashboard.service';
import { LoggerService } from "../share/logger.service";

import { answerCountForAssetTypeService } from "./answer-count-for-asset-type.service"

// import { RecentScoresForAsset } from './scores-for-asset-widget-model'
import { AssetType, QuestionType } from '../model/widget-data'

class RecentScoresForAssetTypeCLS {
  ASSET_TYPE_SYSID?: string | number;
  assettype?: string;
  D_AUDIT_QUESTION_TYPE_CODE?: string;
  AUDIT_ANSWERS_TYPE_VALUE?: string;
  ASSETTYPE_COUNT?: number;
  formattedDate?: Date;
  divID: string;

  AUDIT_Question_TYPE: string;

  error?: string;
  spinner?: boolean;
  colour?: string;
  name?: string;
}

@Component({
  selector: 'answer-count-for-asset-type',
  templateUrl: './answer-count-for-asset-type.component.html',
  styleUrls: ['../default-dash-widget/default-dash-widget.component.css', '../default-dash-widget/widget.css'
    , '../default-dash-widget/acspin.css', './answer-count-for-asset-type.component.css']
  , providers: [answerCountForAssetTypeService]
})


//start of 'Answer Count for Asset Type' //'Scores for AssetType'},
export class AnswerCountForAssetTypeComponent implements OnInit {//, AfterViewInit, OnChanges,DoCheck
  // @ViewChild('ac1') wjanswers: wjNg2Input.WjAutoComplete;
  @ViewChild('wjanswers') wjanswers: wjNg2Input.WjAutoComplete;
  @Input()
  RecentSFAT: WidgetModel;


  public flipState: string;
  public recentScoresForAssetType: RecentScoresForAssetTypeCLS;
  public assetTypeList: AssetType[];

  public AnswersArray: string[];//any;//$scope.AnswersArray=[];
  public QuestionType: QuestionType[];

  private debug: boolean = false;
  constructor(
    private dbdService: DashboardService,
    private logger: LoggerService,
    private selfService: answerCountForAssetTypeService

  ) {
    this.recentScoresForAssetType = new RecentScoresForAssetTypeCLS();
    this.AnswersArray = [];
    this.QuestionType = [];

    this.flipState = this.dbdService.flipState.Backward;//.Forward;
    this.debug = this.dbdService.getDebug();
  }
  // ngAfterViewInit() {
  //   // this.logger.consoleLog("-------------------");
  //   // this.logger.consoleLog("this.ac12.selectedValue:" + this.wjanswers.selectedValue);
  // }
  /**
   * Logic 1.-->getQuestionType() 2.-->loadAssetTypeList() 3.-->getQuestionTypebyAssetType() 4.-->getQuestionTypebyAssetType()
   */
  ngOnInit() {
    //  this.loadAssetTypeList();
    this.getQuestionType();


    this.recentScoresForAssetType.assettype = this.RecentSFAT.assettype;//.dashassettype;// dashassettype="item.assettype" 
    this.recentScoresForAssetType.ASSET_TYPE_SYSID = this.RecentSFAT.ASSET_TYPE_SYSID;//.dashassettypesysid;// dashassettypesysid="item.ASSET_TYPE_SYSID"> 
    this.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE
      = this.RecentSFAT.D_AUDIT_QUESTION_TYPE_CODE;//dashauditquestiontypecode;// dashauditquestiontypecode="item.D_AUDIT_QUESTION_TYPE_CODE" 
    this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE
      = this.RecentSFAT.AUDIT_ANSWERS_TYPE_VALUE;// dashassetanswertypevalue="item.AUDIT_ANSWERS_TYPE_VALUE" 
    this.recentScoresForAssetType.ASSETTYPE_COUNT = 0;
    this.recentScoresForAssetType.formattedDate = new Date();
    //$scope.recentScoresForAssetType.flipState="flipForward";
    this.recentScoresForAssetType.spinner = false;
    this.recentScoresForAssetType.divID = this.RecentSFAT.widgetIndex + "";//assetitemnumber;// assetitemnumber="item.widgetIndex"


    this.initAnswerValues();
    this.loadRecentScoresForAssetType();

    // ?RecentSFAT.refleshW();
  }
  private initAnswerValues() {
    let obj: RecentScoresForAssetTypeCLS = this.recentScoresForAssetType;
    this.getAnswerTypebyQuestionType(obj);
    // this.recentScoresForAssetType.spinner=true; 
    //   dataDashboardFactory.getAnswerTypebyQuestionType
    //   ($scope.recentScoresForAssetType.ASSET_TYPE_SYSID,
    //    $scope.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE)
    //               .success(function(data) {
    //                         if(angular.isArray(data)){
    //                           var o =_.each(data,  function(e,i,l){ 
    //                            l[i]=e.value; 
    //                           });
    //                           $scope.AnswersArray = o;
    //                           $timeout(function() {
    //               $scope.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE
    //                =RecentSFAT.dashassetanswertypevalue;
    //              },0);  
    //                         }

    //                         _.defer(function(){
    // //                         $scope.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE
    // //               =RecentSFAT.dashassetanswertypevalue;

    //                             $scope.$apply();
    //                         });
    //                         $scope.recentScoresForAssetType.spinner=false; 
    //                 }).error(function(data) {
    //                         $log.log("An error occured getQuestionTypebyAssetType: ",data);
    //                         $scope.recentScoresForAssetType.spinner=false; 
    //                 });
  }
  private getAnswerTypebyQuestionType(paramObj: RecentScoresForAssetTypeCLS) {
    var tempObj = this;
     this.logger.consoleLog("==>this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE1:" + this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE);
    let objA: RecentScoresForAssetTypeCLS = Object.assign({}, tempObj.recentScoresForAssetType);

    tempObj.recentScoresForAssetType.spinner = true;
    tempObj.AnswersArray = [];
 this.logger.consoleLog("==>this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE2:" + this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE);
    this.selfService.getAnswerTypebyQuestionType(paramObj)
      .map(res => res.json())
      .subscribe(
      data => {
        this.logger.consoleLog("==>this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE4:" + this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE);
        if (Array.isArray(data)) {
          this.logger.consoleLog("==>this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE5:" + this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE);
          let o:Array<string>= [];
           o = _.each(data, function (e, i, l) {
            l[i] = e.value;
          });
          // let r:any=o.find(x=>x===tempObj.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE );
          //  tempObj.logger.consoleLog("======>find:"+r);//.find(tempObj.recentScoresForAssetType));
          // if(!r){
          //   o.push(tempObj.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE);
          // }
          tempObj.AnswersArray = o;

          tempObj.wjanswers.itemsSource = tempObj.AnswersArray;
          tempObj.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE = objA.AUDIT_ANSWERS_TYPE_VALUE;
          tempObj.wjanswers.text = tempObj.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE;
          //Database has garbage data.
          //if AUDIT_ANSWERS_TYPE_VALUE is not in tempObj.AnswersArray, AUDIT_ANSWERS_TYPE_VALUE will be null.
          // tempObj.wjanswers.selectedValue=objA.AUDIT_ANSWERS_TYPE_VALUE; 
          // tempObj.wjanswers.refresh(); //if AUDIT_ANSWERS_TYPE_VALUE is not in tempObj.AnswersArray,AUDIT_ANSWERS_TYPE_VALUE will be null.
        }
        tempObj.recentScoresForAssetType.spinner = false;
      },
      error => {
        tempObj.recentScoresForAssetType.spinner = false;
        if (tempObj.debug) {
          tempObj.recentScoresForAssetType.error = <any>error;
          tempObj.logger.consoleLog("agsi---errorMessage of getAnswerTypebyQuestionType() in answer-count-for-asset-type.component :" + this.recentScoresForAssetType.error);
        }
      }
      
      );
    // dataDashboardFactory.getAnswerTypebyQuestionType
    // ($scope.recentScoresForAssetType.ASSET_TYPE_SYSID,
    //  $scope.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE)
    //             .success(function(data) {
    //                       if(angular.isArray(data)){
    //                         var o =_.each(data,  function(e,i,l){ 
    //                          l[i]=e.value; 
    //                         });
    //                         $scope.AnswersArray = o;
    //                       }
    //                       _.defer(function(){
    //                           $scope.$apply();
    //                       });
    //                       $scope.recentScoresForAssetType.spinner=false; 
    //               }).error(function(data) {
    //                       $log.log("An error occured getQuestionTypebyAssetType: ",data);
    //                       $scope.recentScoresForAssetType.spinner=false; 
    //               });
     this.logger.consoleLog("==>this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE3:" + this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE);
  }

  private setAssetTypeName() {
    if (!this.assetTypeList) return;
    try {
      let searchObj = { "ASSETTYPE_SYSID": "" };
      //     searchObj.name = $scope.recentScoresForAssetType.assettype;
      //     var temAsset = _.findWhere($scope.assetTypeList, searchObj); 
      //     $scope.recentScoresForAssetType.ASSET_TYPE_SYSID = temAsset.ASSETTYPE_SYSID?temAsset.ASSETTYPE_SYSID:temAsset.ASSET_TYPE_SYSID;
      searchObj.ASSETTYPE_SYSID = this.recentScoresForAssetType.ASSET_TYPE_SYSID + "";
      var temAsset = _.findWhere(this.assetTypeList, searchObj);
      this.recentScoresForAssetType.assettype = temAsset.name ? temAsset.name : "";
    } catch (err) {
      this.logger.consoleLog("setAssetTypeName: " + err);
    }
  }
  /**
   * Get Question Types of an Asset Type
   */
  private getQuestionTypebyAssetType(AssetTypeChanged:boolean) {
    let selfThis=this;
    let tmpObj = this.recentScoresForAssetType;
    this.setAssetTypeName();
    this.recentScoresForAssetType.spinner = true;
    this.selfService.getQuestionTypebyAssetType(this.recentScoresForAssetType)
      .map(res => res.json())
      .subscribe(
      data => {
        if (Array.isArray(data)) {
          this.QuestionType = data;
          if(AssetTypeChanged&&AssetTypeChanged===true){
            selfThis.getAnswerTypebyQuestionType(tmpObj);
          }
        }
        this.recentScoresForAssetType.spinner = false;
      },
      error => {
        this.recentScoresForAssetType.spinner = false;
        if (this.debug) {
          this.recentScoresForAssetType.error = <any>error;
          this.logger.consoleLog("agsi---errorMessage of getQuestionTypebyAssetType() in answer-count-for-asset-type.component :" + this.recentScoresForAssetType.error);
        }
      }
      );
    // dataDashboardFactory.getQuestionTypebyAssetType($scope.recentScoresForAssetType.ASSET_TYPE_SYSID)
    //             .success(function(data) {
    //                     if(angular.isArray(data)){
    //                       $scope.QuestionType = data;
    //                     }
    //                     _.defer(function(){
    //                         $scope.$apply();
    //                     });
    //                     $scope.recentScoresForAssetType.spinner=false; 
    //             }).error(function(data) {
    //                     $log.log("An error occured getQuestionTypebyAssetType: ",data);
    //                     $scope.recentScoresForAssetType.spinner=false; 
    //             });
  };
  /**
   * @description retrieve all Question Types from the server
   */
  private getQuestionType(): void {
    // var self = this;
    this.recentScoresForAssetType.spinner = true;
    this.selfService.getQuestionType()
      .map(res => res.json())
      .subscribe(
      data => {
        if (Array.isArray(data)) {
          this.QuestionType = data;
          this.loadAssetTypeList();
        }
        else { }
        this.recentScoresForAssetType.spinner = false;
        // this.flipState = "flipBackward";
      },
      error => {
        this.recentScoresForAssetType.spinner = false;
        if (this.debug) {
          this.recentScoresForAssetType.error = <any>error;
          this.logger.consoleLog("agsi---errorMessage of getQuestionType() in answer-count-for-asset-type.component :" + this.recentScoresForAssetType.error);
        }
      }
      );

    // dataDashboardFactory.getQuestionType ()
    //     .success(function(data) {
    //             if(angular.isArray(data)){
    //               $scope.QuestionType = data;
    //             }
    //             _.defer(function(){
    //                 $scope.$apply();
    //             });
    //     }).error(function(data) {
    //             $log.log("An error occured getQuestionType: ",data);
    //     });    

  }
  /**
   * @description retrieve all Asset Types from the server 
   */
  private loadAssetTypeList(): void {
    this.recentScoresForAssetType.spinner = true;
    this.selfService.getListOfAssetTypes(null)
      .map(res => res.json())
      .subscribe(
      data => {
        if (Array.isArray(data)) {
          this.assetTypeList = data;
          this.getQuestionTypebyAssetType(null);
        }
        else {
        }
        this.recentScoresForAssetType.spinner = false;
        // this.flipState = "flipBackward";
      },
      error => {
        this.recentScoresForAssetType.spinner = false;
        if (this.debug) {
          this.recentScoresForAssetType.error = <any>error;
          this.logger.consoleLog("agsi---errorMessage of loadAssetTypeList() in answer-count-for-asset-type.component :" + this.recentScoresForAssetType.error);
        }
      }
      );

    // dataDashboardFactory.getListOfAssetTypes()
    //     .success(function(data) {
    //     $scope.assetTypeList = data;
    //      _.defer(function(){
    //             $scope.$apply();
    //         });
    //     //$log.log("$scope.assetTypeList",$scope.assetTypeList);
    // }).
    // error(function(data) {
    //     $log.log(data + ' ' + status);
    // });	

  }

  private loadRecentScoresForAssetType() {
    this.recentScoresForAssetType.spinner = true;
    this.recentScoresForAssetType.error = null;
    var tmpRecentSFAT = this.recentScoresForAssetType;
    if (!tmpRecentSFAT.ASSET_TYPE_SYSID ||
      !tmpRecentSFAT.AUDIT_ANSWERS_TYPE_VALUE ||
      !tmpRecentSFAT.D_AUDIT_QUESTION_TYPE_CODE) {
      //$scope.recentScoresForAssetType = (JSON.parse(dashWidget));
      this.recentScoresForAssetType.spinner = false;
    } else {

      //                     dataDashboardFactory.recentScoresForAssetType(
      //                     tmpRecentSFAT.ASSET_TYPE_SYSID
      //                     ,tmpRecentSFAT.AUDIT_ANSWERS_TYPE_VALUE
      //                     ,tmpRecentSFAT.D_AUDIT_QUESTION_TYPE_CODE
      //                     ,tmpRecentSFAT.AUDIT_Question_TYPE)
      this.selfService.recentScoresForAssetType(tmpRecentSFAT)
        .map(res => res.json())
        .subscribe(
        data => {

          if (Array.isArray(data)) {
            // Start of deprecate codes
            // if (!data[0].AUDIT_Question_TYPE && tmpRecentSFAT && tmpRecentSFAT.AUDIT_Question_TYPE) {
            //   data[0].AUDIT_Question_TYPE = tmpRecentSFAT.AUDIT_Question_TYPE;
            // }
            // End of the start of deprecate codes
            var Obj2 = data[0];
            // angular.extend(this.recentScoresForAssetType, Obj2);
            Object.assign(this.recentScoresForAssetType, Obj2);

            // this.flipState = this.dbdService.flipState.Backward;
          }
          else {
            this.recentScoresForAssetType.ASSET_TYPE_SYSID = tmpRecentSFAT.ASSET_TYPE_SYSID;
            this.recentScoresForAssetType.assettype = tmpRecentSFAT.assettype ? tmpRecentSFAT.assettype : null;
            this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE = tmpRecentSFAT.AUDIT_ANSWERS_TYPE_VALUE;
            this.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE
              = tmpRecentSFAT.D_AUDIT_QUESTION_TYPE_CODE ? tmpRecentSFAT.D_AUDIT_QUESTION_TYPE_CODE : null;
            if (data && data.result == 0) {
              this.recentScoresForAssetType.ASSETTYPE_COUNT = 0;
            } else
              if (data && data.error && this.debug) {
                if (this.recentScoresForAssetType) {
                  this.recentScoresForAssetType.error = data.error;
                } else {
                  this.recentScoresForAssetType.error = data.error;
                }
              } else if (this.debug) {
                this.recentScoresForAssetType.error = "Error is here.";
              }
            // this.flipState = this.dbdService.flipState.Forward;
          }
          this.recentScoresForAssetType.spinner = false;
          // this.flipState = "flipBackward";
        },
        error => {
          this.recentScoresForAssetType.spinner = false;
          // this.flipState =  this.dbdService.flipState.Backward;//"flipBackward";
          if (this.debug) {
            this.recentScoresForAssetType.error = <any>error;
            this.logger.consoleLog("agsi---errorMessage of loadAssetTypeList() in answer-count-for-asset-type.component :" + this.recentScoresForAssetType.error);
          }
        }
        );
    }
  }

  private updateRecentSFAT(): void {
    let assettype = this.recentScoresForAssetType.assettype;
    //                    var searchObj = {};
    //                    searchObj.name = $scope.recentScoresForAssetType.assettype;
    //                    var temAsset = _.findWhere($scope.assetTypeList, searchObj); 
    //                    $scope.recentScoresForAssetType.ASSET_TYPE_SYSID = temAsset.ASSETTYPE_SYSID?temAsset.ASSETTYPE_SYSID:temAsset.ASSET_TYPE_SYSID;
    //                    
    // this.RecentSFAT.dashassettype = $scope.recentScoresForAssetType.assettype;
    this.RecentSFAT.assettype = this.recentScoresForAssetType.assettype;// dashassettype="item.assettype"
    // this.recentScoresForAssetType.ASSET_TYPE_SYSID = this.RecentSFAT.ASSET_TYPE_SYSID;//.dashassettypesysid;// dashassettypesysid="item.ASSET_TYPE_SYSID"> 
    this.RecentSFAT.ASSET_TYPE_SYSID = this.recentScoresForAssetType.ASSET_TYPE_SYSID;
    // this.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE=this.RecentSFAT.D_AUDIT_QUESTION_TYPE_CODE;//dashauditquestiontypecode;// dashauditquestiontypecode="item.D_AUDIT_QUESTION_TYPE_CODE" 
    this.RecentSFAT.D_AUDIT_QUESTION_TYPE_CODE
      = this.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE;
    // this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE
    //       = this.RecentSFAT.AUDIT_ANSWERS_TYPE_VALUE;// dashassetanswertypevalue="item.AUDIT_ANSWERS_TYPE_VALUE" 
    this.RecentSFAT.AUDIT_ANSWERS_TYPE_VALUE
      = this.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE;

    this.logger.consoleLog("this.RecentSFAT:" + JSON.stringify(this.RecentSFAT));
    this.logger.consoleLog("this.recentScoresForAssetType:" + JSON.stringify(this.recentScoresForAssetType));




  }

  flipWidget() {
    if (this.flipState == this.dbdService.flipState.Forward) {
      // this.renderer.detachView;
      this.flipState = this.dbdService.flipState.Backward;//="flipBackward";

      this.dbdService.saveDataOfWidgets();
      this.setAssetTypeName();
      if (this.recentScoresForAssetType && this.recentScoresForAssetType.ASSETTYPE_COUNT) {
        this.recentScoresForAssetType.ASSETTYPE_COUNT = 0;
      }
      this.updateRecentSFAT();
      this.loadRecentScoresForAssetType();
      // ?RecentSFAT.refleshW();     

    } else {
      // ?RecentSFAT.stopRefleshW();
      this.flipState = this.dbdService.flipState.Forward;//"flipForward";
    }
    this.logger.log("flipWidget");

  }
  /**
   * @function This function matchs the function deleteThisWidget() in AngualrJS
   * function deleteThisWidget() {$scope.$emit("deleteDashWidget", assetitemnumber);};
   * @description Remove this widget from on the panel page. 
   */
  RemoveDefault() {
    // this.remove.emit(this.defaultData.widgetIndex);
    this.dbdService.removeWidgetOnPage(this.RecentSFAT.widgetIndex);
  }

  // public onChangeAssetType(e:Event):void{
  //   //  this.logger.consoleLog("0-----");
  //   //  this.logger.consoleLog(e);
  //   //  this.logger.consoleLog(e.target);
  //   //  this.logger.consoleLog(event.target as SelectElement).value);
  // }
  public onChangeAssetType2(e: Event): void {
    //  this.logger.consoleLog("2-----");
    //  this.logger.consoleLog(e);
    //  this.logger.consoleLog(e.target);
    //  this.logger.consoleLog(this.recentScoresForAssetType.ASSET_TYPE_SYSID);
    let AssetTypeChanged:boolean=true;
    this.getQuestionTypebyAssetType(AssetTypeChanged);
    //  this.logger.consoleLog(event.target as SelectElement).value);
  }
  public onChangeQuestionType(e: Event): void {
    let obj: any = this.recentScoresForAssetType;
    this.getAnswerTypebyQuestionType(obj);
  }
  public answersTextChanged(e: Event): void {
    // this.logger.consoleLog("answersTextChanged:" + e);
  }

  // ngOnChanges(changes: SimpleChanges) {
  //   // this.logger.consoleLog("=1===================")
  //   // this.logger.consoleLog(changes)
  //   // this.logger.consoleLog("===2=================")
  //   // for (let propName in changes) {
  //   //   let chng = changes[propName];
  //   //   let cur = JSON.stringify(chng.currentValue);
  //   //   let prev = JSON.stringify(chng.previousValue);
  //   //   this.logger.consoleLog(`${propName}: currentValue = ${cur}, previousValue = ${prev}`);
  //   // }
  // }

  //   oldD_AUDIT_QUESTION_TYPE_CODE="";
  //   ngDoCheck() {
  //   if(this.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE!==this.oldD_AUDIT_QUESTION_TYPE_CODE){
  //     this.logger.consoleLog("==>this.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE: "+this.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE);
  //     this.oldD_AUDIT_QUESTION_TYPE_CODE=this.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE;
  //   }
  //   this.logger.consoleLog("==>this.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE: "+this.oldD_AUDIT_QUESTION_TYPE_CODE);


  // }
}
//end of 'Answer Count for Asset Type' //'Scores for AssetType'},

//start of 'Answer Count for Asset Type' //'Scores for AssetType'},
// go360App.component('recentScoresForAssetType', {
// 	bindings: {
// 		dashassettypesysid: '=',
// 		dashassetanswertypevalue: '=',
// 		dashauditquestiontypecode: '=',
// 		assetitemnumber: '=',
//                 dashassettype: '='
// 	},

//         templateUrl: 'partials/dir-dashRecentScoresForAssetType.html',

//         controller: ['$interval','$log','dataFactory','dataDashboardFactory',
//         '$rootScope','$scope','$sessionStorage','$timeout', 'currentState', 
//         function scoresByCategory($interval,$log, dataFactory,dataDashboardFactory,
//         $rootScope,$scope,$sessionStorage,$timeout, currentState){

//             var RecentSFAT = this;
//             var assettype = RecentSFAT.dashassettype||"";
//             var ASSET_TYPE_SYSID = RecentSFAT.dashassettypesysid || 0;
//             var AUDIT_ANSWERS_TYPE_VALUE = RecentSFAT.dashassetanswertypevalue || 0;
//             var AUDIT_Question_TYPE=null;//Question Type
//             var D_AUDIT_QUESTION_TYPE_CODE = RecentSFAT.dashauditquestiontypecode || "";
//             var assetitemnumber = RecentSFAT.assetitemnumber;
//             $scope.AnswersArray=[];
//             RecentSFAT.$onInit = function() {
//               $scope.recentScoresForAssetType={};
//               $scope.recentScoresForAssetType.assettype=RecentSFAT.dashassettype;
//               $scope.recentScoresForAssetType.ASSET_TYPE_SYSID=RecentSFAT.dashassettypesysid;
//               $scope.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE
//                =RecentSFAT.dashauditquestiontypecode;
//               $scope.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE
//                =RecentSFAT.dashassetanswertypevalue;
//               $scope.recentScoresForAssetType.ASSETTYPE_COUNT=0;
//               $scope.recentScoresForAssetType.formattedDate=new Date();
//               //$scope.recentScoresForAssetType.flipState="flipForward";
//               $scope.recentScoresForAssetType.spinner=false;
//               $scope.recentScoresForAssetType.divID=assetitemnumber;
//               loadRecentScoresForAssetType();
//               RecentSFAT.initAnswerValues();
//               RecentSFAT.refleshW();
//             }
//             $scope.dashBoardAuditAnswerTypeValues = [
//               {id: '1', answerTypeValue: 'ABCDE'}
// //              ,
// //              {id: '2', answerTypeValue: '1234'},
// //              {id: '3', answerTypeValue: 'CCCC'},
// //              {id: '4', answerTypeValue: 'DDDD'},
// //              {id: '5', answerTypeValue: 'EEEE'}
//             ];


//             var dashWidget = JSON.stringify({
//                 "dashtype": "scores_for_assettype",
//                 "assetID": "0",
//                 "ASSET_SYSID": "0",
//                 "ASSET_TYPE_SYSID": "0",
//                 "name": "Scores for Asset",
//                 "assettype": "",
//                 "flipState": "flipForward",
//                 "AUDIT_ANSWERS_TYPE_VALUE": "A",
//                 "D_AUDIT_QUESTION_TYPE_CODE": 10,
//                 "widgetIndex": 99
//             })
//          loadAssetTypeList();   
//          getQuestionType();
// $scope.$watch('recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE', function(newValue, oldValue) {
//  if($scope.recentScoresForAssetType.flipState&&$scope.recentScoresForAssetType.flipState==="flipForward"){
//   RecentSFAT.getAnswerTypebyQuestionType();
//  }
// }); 
// RecentSFAT.initAnswerValues=function(){
//   $scope.recentScoresForAssetType.spinner=true; 
//   dataDashboardFactory.getAnswerTypebyQuestionType
//   ($scope.recentScoresForAssetType.ASSET_TYPE_SYSID,
//    $scope.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE)
//               .success(function(data) {
//                         if(angular.isArray(data)){
//                           var o =_.each(data,  function(e,i,l){ 
//                            l[i]=e.value; 
//                           });
//                           $scope.AnswersArray = o;
//                           $timeout(function() {
//               $scope.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE
//                =RecentSFAT.dashassetanswertypevalue;
//              },0);  
//                         }

//                         _.defer(function(){
// //                         $scope.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE
// //               =RecentSFAT.dashassetanswertypevalue;

//                             $scope.$apply();
//                         });
//                         $scope.recentScoresForAssetType.spinner=false; 
//                 }).error(function(data) {
//                         $log.log("An error occured getQuestionTypebyAssetType: ",data);
//                         $scope.recentScoresForAssetType.spinner=false; 
//                 });
// }

// RecentSFAT.getAnswerTypebyQuestionType=function(){
//   $scope.recentScoresForAssetType.spinner=true; 
//   $scope.AnswersArray=[];
//   dataDashboardFactory.getAnswerTypebyQuestionType
//   ($scope.recentScoresForAssetType.ASSET_TYPE_SYSID,
//    $scope.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE)
//               .success(function(data) {
//                         if(angular.isArray(data)){
//                           var o =_.each(data,  function(e,i,l){ 
//                            l[i]=e.value; 
//                           });
//                           $scope.AnswersArray = o;
//                         }
//                         _.defer(function(){
//                             $scope.$apply();
//                         });
//                         $scope.recentScoresForAssetType.spinner=false; 
//                 }).error(function(data) {
//                         $log.log("An error occured getQuestionTypebyAssetType: ",data);
//                         $scope.recentScoresForAssetType.spinner=false; 
//                 });
// }
// $scope.$watch('recentScoresForAssetType.ASSET_TYPE_SYSID', function(newValue, oldValue) {
//  if($scope.recentScoresForAssetType.flipState&&$scope.recentScoresForAssetType.flipState==="flipForward"){
//   RecentSFAT.getQuestionTypebyAssetType();
//  }
// });   
//    RecentSFAT.getQuestionTypebyAssetType=function(){
//     setAssetTypeName();
//     $scope.recentScoresForAssetType.spinner=true; 
//     dataDashboardFactory.getQuestionTypebyAssetType($scope.recentScoresForAssetType.ASSET_TYPE_SYSID)
//                 .success(function(data) {
//                         if(angular.isArray(data)){
//                           $scope.QuestionType = data;
//                         }
//                         _.defer(function(){
//                             $scope.$apply();
//                         });
//                         $scope.recentScoresForAssetType.spinner=false; 
//                 }).error(function(data) {
//                         $log.log("An error occured getQuestionTypebyAssetType: ",data);
//                         $scope.recentScoresForAssetType.spinner=false; 
//                 });
//    };     
//    function setAssetTypeName(){
//     if(!$scope.assetTypeList) return;
//     try{
//      var searchObj = {};
// //     searchObj.name = $scope.recentScoresForAssetType.assettype;
// //     var temAsset = _.findWhere($scope.assetTypeList, searchObj); 
// //     $scope.recentScoresForAssetType.ASSET_TYPE_SYSID = temAsset.ASSETTYPE_SYSID?temAsset.ASSETTYPE_SYSID:temAsset.ASSET_TYPE_SYSID;

// searchObj.ASSETTYPE_SYSID=$scope.recentScoresForAssetType.ASSET_TYPE_SYSID;
// var temAsset=_.findWhere($scope.assetTypeList, searchObj);
// $scope.recentScoresForAssetType.assettype = temAsset.name?temAsset.name:"";
//     }catch(err){
//       $log.log("setAssetTypeName: "+err);
//     }
//    }     
//   $scope.$on('stopWidgetInterval', function() {
// //    $scope.stopWidgetInterval=true;
//     RecentSFAT.stopRefleshW();
//   });        
//   var intervalTime=30000;
// //  var intervalRSFAT= setInterval(function(){ 
// //     if(!$scope.stopWidgetInterval){
// //      loadRecentScoresForAssetType(); 
// //     }
// //  }, intervalTime);
//   //clearInterval(intervalRSFAT);          
// var stopWidgetInterval;
//         RecentSFAT.refleshW = function(){
//           if ( angular.isDefined(stopWidgetInterval) ) return;
//           stopWidgetInterval = $interval(function() {
//             loadRecentScoresForAssetType(); 
//           }, intervalTime);
//         };

//         RecentSFAT.stopRefleshW = function() {
//           if (angular.isDefined(stopWidgetInterval)) {
//             $interval.cancel(stopWidgetInterval);
//             stopWidgetInterval = undefined;
//           }
//         };
//  $scope.$on('$destroy', function() {
//           // Make sure that the interval is destroyed too
//           RecentSFAT.stopRefleshW();
//         });         

//          function getQuestionType(){
//              dataDashboardFactory.getQuestionType ()
//                 .success(function(data) {
//                         if(angular.isArray(data)){
//                           $scope.QuestionType = data;
//                         }
//                         _.defer(function(){
//                             $scope.$apply();
//                         });
//                 }).error(function(data) {
//                         $log.log("An error occured getQuestionType: ",data);
//                 });
//          }

// 	function loadRecentScoresForAssetType() {
//                 $scope.recentScoresForAssetType.spinner=true; 
//                 var tmpRecentSFAT=$scope.recentScoresForAssetType;
//                 if(!tmpRecentSFAT.ASSET_TYPE_SYSID ||
//                    !tmpRecentSFAT.AUDIT_ANSWERS_TYPE_VALUE ||
//                    !tmpRecentSFAT.D_AUDIT_QUESTION_TYPE_CODE) {
//                     //$scope.recentScoresForAssetType = (JSON.parse(dashWidget));
//                     $scope.recentScoresForAssetType.spinner=false;
//                 } else {
//                     dataDashboardFactory.recentScoresForAssetType(
//                     tmpRecentSFAT.ASSET_TYPE_SYSID
//                     ,tmpRecentSFAT.AUDIT_ANSWERS_TYPE_VALUE
//                     ,tmpRecentSFAT.D_AUDIT_QUESTION_TYPE_CODE
//                     ,tmpRecentSFAT.AUDIT_Question_TYPE)
//                         .success(function(data) {
//                         //$log.log("Succesfully fetching the asset by type: ",data);
//                         if(angular.isArray(data)){
//                           //if($scope.recentScoresForAssetType&&$scope.recentScoresForAssetType.error){$scope.recentScoresForAssetType.error=null;}
//                           if(!data[0].AUDIT_Question_TYPE&&tmpRecentSFAT&&tmpRecentSFAT.AUDIT_Question_TYPE){
//                             data[0].AUDIT_Question_TYPE=tmpRecentSFAT.AUDIT_Question_TYPE;
//                             }
// //                          $scope.recentScoresForAssetType = data[0];//setFormattersOnAssetType(data);
//                           var Obj2=data[0];//setFormattersOnAssetType(data[0]);
//                           angular.extend($scope.recentScoresForAssetType, Obj2);
//                         }else{
//                              //$scope.recentScoresForAssetType={}; 
// //                              ASSET_TYPE_SYSID,AUDIT_ANSWERS_TYPE_VALUE,D_AUDIT_QUESTION_TYPE_CODE,AUDIT_Question_TYPE
//                               $scope.recentScoresForAssetType.ASSET_TYPE_SYSID=tmpRecentSFAT.ASSET_TYPE_SYSID;
//                               $scope.recentScoresForAssetType.assettype=tmpRecentSFAT.assettype?tmpRecentSFAT.assettype:null;
//                               $scope.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE=tmpRecentSFAT.AUDIT_ANSWERS_TYPE_VALUE;
//                               $scope.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE
//                               =tmpRecentSFAT.D_AUDIT_QUESTION_TYPE_CODE?tmpRecentSFAT.D_AUDIT_QUESTION_TYPE_CODE:null;

//                           //$scope.recentScoresForAssetType=data;
//                           if(data&&data.result==0){
//                             $scope.recentScoresForAssetType.ASSETTYPE_COUNT=0;
//                           }else
//                           if(data&&data.error){
//                             if($scope.recentScoresForAssetType){
//                              $scope.recentScoresForAssetType.error=data.error;
//                             }else{
//                               $scope.recentScoresForAssetType.error=data.error;
//                             }
//                           }else{
//                             $scope.recentScoresForAssetType.error="Error is here.";
//                           }

//                         }
//                         _.defer(function(){
//                             $scope.$apply();
//                         });
//                         $scope.recentScoresForAssetType.spinner=false;
//                     }).
//                     error(function(data) {
//                         $scope.recentScoresForAssetType.spinner=false;
//                         $log.log("An error occured fetching the asset: ",data);
//                     });	
//                 }
// 	}

//         function loadAssetTypeList() {
//                 dataDashboardFactory.getListOfAssetTypes()
//                     .success(function(data) {
//                     $scope.assetTypeList = data;
//                      _.defer(function(){
//                             $scope.$apply();
//                         });
//                     //$log.log("$scope.assetTypeList",$scope.assetTypeList);
//                 }).
//                 error(function(data) {
//                     $log.log(data + ' ' + status);
//                 });	
//             }

//         function setFormattersOnAssetType(data) {
//                 if(data) {
//                     data = _.map(data, function(score) {
//                             score.icon = '';
//                             score.colour = 'black';
// //                            try{
// //                            if(score.USER_MODIFY_DATETIME){
// //                              score.formattedDate = new Date(score.USER_MODIFY_DATETIME);
// //                            }
// //                            }catch(err){
// //                              $log.log("setFormattersOnAssetType "+err);
// //                            }
// //                            if(!(typeof score.formattedDate === 'object') ){
// //                              score.formattedDate=new Date();
// //                            }
//                             score.formattedDate=_agsi_date(score.USER_MODIFY_DATETIME);
//                             score.dashtype = "scores_for_assettype";
//                             return score;
//                          });
//                     //$log.log("processed Sub Assets returned data object", data);
//                     }
//                 //$log.log("PlusMinus: ",data[0]);
// 				return data[0];
//         }

//         function flipWidget() {
// 	  if($scope.recentScoresForAssetType.flipState=="flipForward") {
//                     $scope.recentScoresForAssetType.flipState="flipBackward"
//                     setAssetTypeName();
//                     assettype = $scope.recentScoresForAssetType.assettype;
// //                    var searchObj = {};
// //                    searchObj.name = $scope.recentScoresForAssetType.assettype;
// //                    var temAsset = _.findWhere($scope.assetTypeList, searchObj); 
// //                    $scope.recentScoresForAssetType.ASSET_TYPE_SYSID = temAsset.ASSETTYPE_SYSID?temAsset.ASSETTYPE_SYSID:temAsset.ASSET_TYPE_SYSID;
// //                    
//                     RecentSFAT.dashassettype=$scope.recentScoresForAssetType.assettype;
//                     RecentSFAT.dashassettypesysid=$scope.recentScoresForAssetType.ASSET_TYPE_SYSID;
//                     RecentSFAT.dashauditquestiontypecode
//                       =$scope.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE;
//                     RecentSFAT.dashassetanswertypevalue
//                       =$scope.recentScoresForAssetType.AUDIT_ANSWERS_TYPE_VALUE;

//                     if($scope.recentScoresForAssetType&&$scope.recentScoresForAssetType.ASSETTYPE_COUNT){
//                          $scope.recentScoresForAssetType.ASSETTYPE_COUNT=0;
//                     }
//                     loadRecentScoresForAssetType();
//                     RecentSFAT.refleshW();
// 		} else {
//                     RecentSFAT.stopRefleshW();
//                     $scope.recentScoresForAssetType.flipState="flipForward"
// 				};
// 			}




//             function deleteThisWidget() {
//                 $scope.$emit("deleteDashWidget", assetitemnumber);
//             };

// 			this.deleteThisWidget = deleteThisWidget;	
//             //this.loadScores = loadRecentScoresForAssetType;		
// 			this.loadAssetTypeList = loadAssetTypeList;		
// 			this.setFormattersOnAssetType = setFormattersOnAssetType;	
// 			this.flipWidget = flipWidget;	

//         }]

// });

//the end of go360App.component('recentScoresForAssetType')////////////////////////////////////////////////