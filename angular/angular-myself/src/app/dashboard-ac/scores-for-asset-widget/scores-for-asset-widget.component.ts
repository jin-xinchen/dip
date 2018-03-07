import { Component, OnInit, Input, Inject } from '@angular/core';
import { NgForm,FormsModule } from '@angular/forms';
// import { Http } from '@angular/http';

//underscore@1.8.3
import * as _ from 'underscore';
//dashboard
import { WidgetModel, scoresForAsset, AssetLayer } from '../model/widget-model'
import { DashboardService } from '../share/dashboard.service';
import { LoggerService } from '../share/logger.service';

import { scoresForAssetService } from './scores-for-asset-widget.service'
import { RecentScoresForAsset } from './scores-for-asset-widget-model'

function _agsi_date(date) {
  var d = new Date(); var d1 = date;
  try {
    if (d1) {
      d = new Date(d1);
    }
  } catch (err) {
    this.logger.consoleLog("Error: _agsi_date() " + err.message);
  }
  if (!(typeof d === 'object') || (d.toString() == 'Invalid Date')) {
    d = new Date();
  }
  return d;
}

@Component({
  selector: 'scores-for-asset-widget',
  templateUrl: './scores-for-asset-widget.component.html',
  styleUrls: ['../default-dash-widget/default-dash-widget.component.css'
    , '../default-dash-widget/widget.css'
    , '../default-dash-widget/acspin.css', './scores-for-asset-widget.component.css']
  , providers: [scoresForAssetService]
})
export class ScoresForAssetWidgetComponent implements OnInit {

  @Input()
  RecentSFA: WidgetModel;


  flipState: string;
  public recentScoresForAsset: RecentScoresForAsset;// = {"name":"","ASSET_CONTROL_SYSID":"0","ASSET_SYSID":"","USER_MODIFY_DATETIME":"" };
  public AssetLayers: AssetLayer[];
  errorMessage: string;
  private TmpObject:any;
  constructor(
    // private http:Http,
    private dbdService: DashboardService,
    private logger: LoggerService,
    private dataDashboardFactory: scoresForAssetService
  ) {
    this.recentScoresForAsset = Object.assign({});
    this.flipState = this.dbdService.flipState.Backward;//.Forward;
    // this.recentScoresForAsset.name = "name";
    // this.recentScoresForAsset.spinner=true;
    this.AssetLayers = [{ "ASSET_CONTROL_SYSID": 0, "ASSET_LAYER_NAME": "No ASSET LAYER" }];

  }

  ngOnInit() {

    this.RecentSFA_onInit();

  }

  // var assetitemnumber = RecentSFA.assetitemnumber;
  private RecentSFA_onInit() {// RecentSFA.$onInit = function() {
    // $scope.recentScoresForAsset={};
    // assetsysid = "item.ASSET_SYSID"
    // assetitemnumber = "item.widgetIndex"
    // assetcontrolsysid = "item.ASSET_CONTROL_SYSID" >
    this.recentScoresForAsset.ASSET_CONTROL_SYSID = this.RecentSFA.ASSET_CONTROL_SYSID || "";//this.RecentSFA.assetcontrolsysid || "";

    this.recentScoresForAsset.ASSET_SYSID = this.RecentSFA.ASSET_SYSID || "";//.assetsysid || "";
    this.recentScoresForAsset.name = this.recentScoresForAsset.ASSET_SYSID + "";//.assetsysid || "";

    this.recentScoresForAsset.USER_MODIFY_DATETIME = new Date();
    this.recentScoresForAsset.current_D_TOTAL_RATING_SCORE_NUM = 0;
    this.recentScoresForAsset.recent_D_TOTAL_RATING_SCORE_NUM = 0;
    this.recentScoresForAsset.difference = 0;
    this.recentScoresForAsset.formattedDate = this.recentScoresForAsset.USER_MODIFY_DATETIME;
    this.recentScoresForAsset.dashtype = this.RecentSFA.dashtype;//"scores_for_asset";
    this.recentScoresForAsset.spinner = false;
    this.recentScoresForAsset.divID = this.RecentSFA.widgetIndex + "";//assetitemnumber;
    this.flipState = this.dbdService.flipState.Backward;//this.dbdService.flipState.Forward;//"flipBackward";


    //Retrieve the Asset Layers();
    this.retrieveAssetLayers();

    if (this.recentScoresForAsset && this.recentScoresForAsset.ASSET_SYSID) {
      this.loadScores();
    } else {
      this.flipWidget();
    }
    // ?RecentSFA.refleshW();
  }
  /**
   * @description retrieve Asset Layers
   */
  retrieveAssetLayers() {

    // dataDashboardFactory.getAssetControlForDashboard()
    //  .success(function(data) {

    //     if(angular.isArray(data)){
    //           $scope.AssetLayers = data;
    //         }
    //         _.defer(function(){
    //             $scope.$apply();
    //         });
    //  }).
    //  error(function(data) {
    //     $log.log("Error: retrieve AssetLayers,"+data + ' ' + status);
    //  });	
    this.dbdService.getAssetControlForDashboard()
      .subscribe(
      AssetLayers => {
        this.AssetLayers = AssetLayers;
        // if (this.recentScoresForAsset && this.recentScoresForAsset.ASSET_SYSID) {
        //   this.loadScores();
        // } else {
        //   this.flipWidget();
        // }
        // this.logger.consoleLog("AssetLayers:" + AssetLayers);
        // this.logger.consoleLog("this.AssetLayers:" + this.AssetLayers);
      },
      error => {
        this.errorMessage = <any>error;
        this.logger.log("Error: retrieve AssetLayers," + this.errorMessage);
      });
  }
  private initailRecentScoresForAsset() {
    this.recentScoresForAsset.USER_MODIFY_DATETIME = new Date();
    this.recentScoresForAsset.current_D_TOTAL_RATING_SCORE_NUM = 0;
    this.recentScoresForAsset.recent_D_TOTAL_RATING_SCORE_NUM = 0;
    this.recentScoresForAsset.difference = 0;
    this.recentScoresForAsset.formattedDate = this.recentScoresForAsset.USER_MODIFY_DATETIME;
  }
  /**
   * @description load Scores
   */
  private loadScores(): void {
    var debug = false; debug = this.dbdService.getDebug();
    this.errorMessage = null;
    this.recentScoresForAsset.spinner = true;
    var AssetTemp = this.recentScoresForAsset;
    this.initailRecentScoresForAsset();
    if (!AssetTemp || !AssetTemp.ASSET_SYSID) {
      this.recentScoresForAsset.spinner = false;
    } else {
      this.dataDashboardFactory.recentScoresForAsset(AssetTemp)//(AssetTemp.ASSET_SYSID, AssetTemp.ASSET_CONTROL_SYSID)
        .map(res => res.json())
        .subscribe(
        data => {
          if (Array.isArray(data)) {
            //$scope.recentScoresForAsset = setRecentCurrentPlusMinus(data);
            var Obj2 = this.setRecentCurrentPlusMinus(data);
            // angular.extend($scope.recentScoresForAsset, Obj2);
            Object.assign(this.recentScoresForAsset, Obj2);
            this.updateRecentSFA();
            // this.flipState = "flipBackward";
            this.flipState = this.dbdService.flipState.Backward;
          }
          else {
            
            this.recentScoresForAsset.name = AssetTemp.ASSET_SYSID + "";
            this.recentScoresForAsset.ASSET_SYSID = AssetTemp.ASSET_SYSID;
            this.recentScoresForAsset.formattedDate = new Date();
            //  $scope.recentScoresForAsset.USER_MODIFY_DATETIME=new Date();
            if (data && data.result == 0) {
              this.recentScoresForAsset.current_D_TOTAL_RATING_SCORE_NUM = 0;
              this.recentScoresForAsset.recent_D_TOTAL_RATING_SCORE_NUM = 0;
              this.recentScoresForAsset.difference = 0;
              //                            $scope.recentScoresForAsset.formattedDate=new Date();
            }else if(data && data.result == -1003){//-1003 2017-05-19 14:18:13.168-0400 ASSET_SYSID=217621910 doesn't exist in layer=Utility Poles
              this.errorMessage = data.error;
              
            }
             else
              if (data && data.error) {
                if (debug) {
                  // this.recentScoresForAsset.error = data.error;
                  this.errorMessage = data.result + " " + data.error;
                }

              } else {
                if (debug) {
                  // this.recentScoresForAsset.error = "Error is here.";
                  this.errorMessage = "Error is here.";
                }
              }
              this.flipState = this.dbdService.flipState.Forward;
          }
          this.recentScoresForAsset.spinner = false;
          // this.flipState = "flipBackward";
        },
        error => {
          this.recentScoresForAsset.spinner = false;
          this.flipState =  this.dbdService.flipState.Backward;//"flipBackward";
          if (debug) {
            this.errorMessage = <any>error;
            this.logger.consoleLog("agsi---errorMessage of loadScores() in scores-for-asset-widget.component :" + this.errorMessage);
          }
        }
        );
      ;
    }

  }

  private setRecentCurrentPlusMinus(data) {
    console.log('now: ', _.now());
    if (data) {
      data = _.map(data, function (score) {
        score.current_D_TOTAL_RATING_SCORE_NUM = (parseFloat(score.current_D_TOTAL_RATING_SCORE_NUM) * 100).toFixed(1);
        score.recent_D_TOTAL_RATING_SCORE_NUM = (parseFloat(score.recent_D_TOTAL_RATING_SCORE_NUM) * 100).toFixed(1);
        let n: number = (score.current_D_TOTAL_RATING_SCORE_NUM - score.recent_D_TOTAL_RATING_SCORE_NUM);
        let dif: string = (n * (-1)).toFixed(2);
        score.difference = dif;//.toFixed(2)*(-1);
        if (score.difference < 0) {
          score.icon = 'icon-Arrowhead_Down';
          score.colour = 'red';
        } else if (score.difference > 0) {
          score.icon = 'icon-Arrowhead_Up';
          score.colour = 'green';
        } else {
          score.difference = "0";
          score.icon = '';
          score.colour = 'black';
        };
        score.formattedDate = _agsi_date(score.USER_MODIFY_DATETIME);
        return score;
      });
      //$log.log("processed Sub Assets returned data object", data);
    }
    //$log.log("PlusMinus: ",data[0]);
    return data[0];
  }
  private _agsi_date(date) {
    var d = new Date(); var d1 = date;
    try {
      if (d1) {
        d = new Date(d1);
      }
    } catch (err) {
      this.logger.consoleLog("Error: _agsi_date() " + err.message);
    }
    if (!(typeof d === 'object') || (d.toString() == 'Invalid Date')) {
      d = new Date();
    }
    return d;
  }

  private isDate = function (obj) {
    /// <summary>
    /// Determines if the passed object is an instance of Date.
    /// </summary>
    /// <param name="obj">The object to test.</param>

    return Object.prototype.toString.call(obj) === '[object Date]';
  }

  private isValidDate = function (obj) {
    /// <summary>
    /// Determines if the passed object is a Date object, containing an actual date.
    /// </summary>
    /// <param name="obj">The object to test.</param>

    return this.isDate(obj) && !isNaN(obj.getTime());
  }


  flipWidget(f?: NgForm) {

    // this.logger.consoleLog(this.TmpObject);
    //Retrieve the Asset Layers();
    this.retrieveAssetLayers();
    if (this.flipState == this.dbdService.flipState.Forward) {
      // this.renderer.detachView;

      // this.flipState = this.dbdService.flipState.Backward;//="flipBackward";

      // RecentSFA.assetcontrolsysid=$scope.recentScoresForAsset.ASSET_CONTROL_SYSID;
      // RecentSFA.assetsysid=$scope.recentScoresForAsset.ASSET_SYSID;
      // RecentSFA.assetcontrolsysid = this.recentScoresForAsset.ASSET_CONTROL_SYSID;
      if (f) {
        let newObj = f.value;
        if (newObj) {
          if (newObj.assetcontrolsysid) {
            this.recentScoresForAsset.ASSET_CONTROL_SYSID = newObj.assetcontrolsysid;
          }
          // RecentSFA.assetsysid = $scope.recentScoresForAsset.ASSET_SYSID;
          if (newObj.assetsysid) {
            this.recentScoresForAsset.ASSET_SYSID = newObj.assetsysid;
          }
        }
      }

      // loadScores($scope.recentScoresForAsset.ASSET_SYSID, $scope.recentScoresForAsset);
      this.loadScores();
      this.dbdService.saveDataOfWidgets();
      //$log.log("$sessionStorage.userConfig: [recentScoresForAsset]",$sessionStorage.userConfig);
      // ?RecentSFA.refleshW();


    } else {
      this.TmpObject=Object.assign({},this.recentScoresForAsset);
      this.flipState = this.dbdService.flipState.Forward;//"flipForward";
          // this.logger.consoleLog("flipWidget="+this.flipState);
    }
    // this.logger.log("flipWidget");

  }

  /**
   * @function This function matchs the function deleteThisWidget() in AngualrJS
   * function deleteThisWidget() {$scope.$emit("deleteDashWidget", assetitemnumber);};
   * @description Remove this widget from on the panel page. 
   */
  RemoveDefault() {
    // this.remove.emit(this.defaultData.widgetIndex);
    this.dbdService.removeWidgetOnPage(this.RecentSFA.widgetIndex);
  }
  /**
   * @description Update this widget data into Panel for saving Dashboard.
   */
  private updateRecentSFA(): void {
    this.RecentSFA.ASSET_CONTROL_SYSID = this.recentScoresForAsset.ASSET_CONTROL_SYSID;//this.RecentSFA.assetcontrolsysid || "";
    this.RecentSFA.ASSET_SYSID = this.recentScoresForAsset.ASSET_SYSID;//.assetsysid || "";
    // this.RecentSFA.AUDIT_ANSWERS_TYPE_VALUE=this.recent.current_D_TOTAL_RATING_SCORE_NUM;

  }

   cancelDefault(f?: NgForm):void{
    //  this.recentScoresForAsset=this.TmpObject;
    // this.logger.consoleLog(this.recentScoresForAsset);
    this.recentScoresForAsset=Object.assign({},this.TmpObject);
    this.errorMessage=null;
    //  this.recentScoresForAsset.ASSET_SYSID= this.TmpObject.ASSET_SYSID;
    //  this.logger.consoleLog(this.TmpObject);
    //  this.flipState=this.dbdService.flipState.Backward;
//      if (f) {
//         let newObj = f.value;
//         if (newObj) {
//           if (newObj.assetcontrolsysid) {
//              newObj.assetcontrolsysid=this.recentScoresForAsset.ASSET_CONTROL_SYSID;
//           }
//           // RecentSFA.assetsysid = $scope.recentScoresForAsset.ASSET_SYSID;
//           if (newObj.assetsysid) {
//             newObj.assetsysid=this.recentScoresForAsset.ASSET_SYSID;
// this.logger.consoleLog(f.value);
//           }
//         }
//       }
//
   }

}

/**
 * go360App.component('recentScoresForAsset', {
	bindings: {
            assetsysid: '=',
            assetitemnumber: '=',
            assetcontrolsysid:'='
	},
	
        templateUrl: 'partials/dir-dashRecentScoresForAsset.html',
        
        controller: ['$interval','$log','dataFactory','dataDashboardFactory',
        '$rootScope','$scope','$sessionStorage', 'currentState', 
        function scoresByCategory($interval,$log, dataFactory,dataDashboardFactory, 
        $rootScope,$scope,$sessionStorage, currentState){
            
            
            //var ASSET_SYSID = RecentSFA.assetsysid||0;


  $scope.$on('stopWidgetInterval', function() {
    //$scope.stopWidgetInterval=true;
    RecentSFA.stopRefleshW();
  });
  var intervalTime=30000;
//  var intervalSFA= setInterval(function(){ 
////        RecentSFA.assetcontrolsysid=$scope.recentScoresForAsset.ASSET_CONTROL_SYSID;
////        RecentSFA.assetsysid=$scope.recentScoresForAsset.ASSET_SYSID;
//       if(!$scope.stopWidgetInterval){
//        loadScores($scope.recentScoresForAsset.ASSET_SYSID,$scope.recentScoresForAsset);
//       }
//  }, intervalTime);
  //clearInterval(intervalSFA);
//var intervalSFA = $interval(function() {
//    if(!$scope.stopWidgetInterval){
//        loadScores($scope.recentScoresForAsset.ASSET_SYSID,$scope.recentScoresForAsset);
//       }
//    }, intervalTime);  
//      var intervalPromise = $interval(function(){
//        //30sec. Slow this to 30sec?
//        $log.log("Dashboard refreshed");
//    }, 30000); 
//   $interval.cancel(intervalPromise);
var stopWidgetInterval;
        RecentSFA.refleshW = function(){
          if ( angular.isDefined(stopWidgetInterval) ) return;
          stopWidgetInterval = $interval(function() {
            loadScores($scope.recentScoresForAsset.ASSET_SYSID,$scope.recentScoresForAsset);
          }, intervalTime);
        };

        RecentSFA.stopRefleshW = function() {
          if (angular.isDefined(stopWidgetInterval)) {
            $interval.cancel(stopWidgetInterval);
            stopWidgetInterval = undefined;
          }
        };
 $scope.$on('$destroy', function() {
          // Make sure that the interval is destroyed too
          RecentSFA.stopRefleshW();
        });




            $scope.loadAssetList_Dropdown = function($item, $model, $label) {
                $scope.recentScoresForAsset.assettype = $item.name;
                $scope.recentScoresForAsset.ASSET_TYPE_SYSID = $item.ASSET_TYPE_SYSID;
                loadAssetList($item.ASSET_TYPE_SYSID);
            }
            
            function loadAssetList(ASSET_TYPE_SYSID) {
                dataDashboardFactory.getListOfAssets(ASSET_TYPE_SYSID)
                    .success(function(data) {
                    $scope.assetList = data;
                     _.defer(function(){
                            $scope.$apply();
                        });
                }).
                error(function(data) {
                    $log.log(data + ' ' + status);
                });	
            }

            function setRecentCurrentPlusMinus(data) {
                if(data) {
                    data = _.map(data, function(score) {
                        score.current_D_TOTAL_RATING_SCORE_NUM = (parseFloat(score.current_D_TOTAL_RATING_SCORE_NUM)*100).toFixed(1);
                        score.recent_D_TOTAL_RATING_SCORE_NUM = (parseFloat(score.recent_D_TOTAL_RATING_SCORE_NUM)*100).toFixed(1);
                                                
                        score.difference = (score.current_D_TOTAL_RATING_SCORE_NUM - score.recent_D_TOTAL_RATING_SCORE_NUM).toFixed(2)*(-1);
                            if (score.difference < 0) {
                                score.icon = 'icon-Arrowhead_Down';
                                score.colour = 'red';
                            } else if (score.difference > 0) {
                                score.icon = 'icon-Arrowhead_Up';
                                score.colour = 'green';
                            } else {
                                score.icon = '';
                                score.colour = 'black';
                            };
//                        try{
//                            if(score.USER_MODIFY_DATETIME){
//                              score.formattedDate = new Date(score.USER_MODIFY_DATETIME);
//                            }
//                            
//                        }catch(err){
//                           $log.log("Error: setRecentCurrentPlusMinus->recentScoresForAsset "+err);    
//                            }
//                            if(!(typeof score.formattedDate === 'object') ||(score.formattedDate=="Invalid Date")){
//                              score.formattedDate=new Date();
//                            }
                            score.formattedDate=_agsi_date(score.USER_MODIFY_DATETIME);
                            return score;
                         });
                    //$log.log("processed Sub Assets returned data object", data);
                    }
                //$log.log("PlusMinus: ",data[0]);
				return data[0];
			}
			
                function flipWidget() {
                    if($scope.recentScoresForAsset.flipState=="flipForward") {
                        $scope.recentScoresForAsset.flipState="flipBackward"
                        RecentSFA.assetcontrolsysid=$scope.recentScoresForAsset.ASSET_CONTROL_SYSID;
                        RecentSFA.assetsysid=$scope.recentScoresForAsset.ASSET_SYSID;
                        loadScores($scope.recentScoresForAsset.ASSET_SYSID,$scope.recentScoresForAsset);
                        //$log.log("$sessionStorage.userConfig: [recentScoresForAsset]",$sessionStorage.userConfig);
                        RecentSFA.refleshW();
                    } else {
                        RecentSFA.stopRefleshW();
                        $scope.recentScoresForAsset.flipState="flipForward"
                    };
                }

            
            
            


            function updateDashboard() {
                                
                var updateObject ={};
                updateObject.assetitemnumber = assetitemnumber;
                updateObject.itemDetails = $scope.recentScoresForAsset;
                $scope.$emit("updateDashboard", updateObject);
            };

			this.deleteThisWidget = deleteThisWidget;	
			this.updateDashboard = updateDashboard;	
			this.loadScores = loadScores;		
            this.loadAssetList = loadAssetList;
			this.setRecentCurrentPlusMinus = setRecentCurrentPlusMinus;	
			this.flipWidget = flipWidget;	

        }]
    
});

//the end of go360App.component('recentScoresForAsset')////////////////////////////////////////////////
 */
