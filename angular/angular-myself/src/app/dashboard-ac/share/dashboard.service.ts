import { Injectable, VERSION, Inject } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams, RequestMethod } from '@angular/http';
import { APP_BASE_HREF, PlatformLocation } from '@angular/common';
//import {Location, LocationStrategy, PathLocationStrategy} from '@angular/common';
import { DOCUMENT } from '@angular/platform-browser';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
//underscore@1.8.3
import * as _ from 'underscore';
//dashboard
import { WidgetModel, WidgetType, AssetLayer } from '../model/widget-model'
import { LoggerService } from '../share/logger.service';
import { credentials } from '../default-dash-widget/credentials'
import { dev } from '../share/config-dev'


@Injectable()
export class DashboardService {

  /**
  * @description
  * The type marks of the widgets to match dataDashboardFactory.dashWidgetTypes in service_dashboard.js of AngularJS
  */
  public widgetType = {
    "audit_count_per_time_period_for_asset_type": "t7",
    "total_audit_score_count_for_asset_type": "t6",
    "scores_for_asset": "scores_for_asset",
    "scores_for_assettype": "scores_for_assettype",
    "audits_for_asset_type": "audits-for-asset-type",
    "audits_per_week_for_asset_type": "audits-per-week-for-asset-type",
    "alerts_ticker": "alerts-ticker",
    "default_widget": "default_widget"
  }
  /**
   * @description
   * The type marks and lables of the widgets. 
   */
  public dashBoardWidgetTypes: WidgetType[] = [
    { id: '1', dashType: this.widgetType.default_widget, displayName: 'New Item' },
    { id: '2', dashType: this.widgetType.scores_for_asset, displayName: 'Scores For Asset' },
    { id: '3', dashType: this.widgetType.scores_for_assettype, displayName: 'Answer Count For Asset Type' },//'Scores for AssetType'},
    { id: '4', dashType: this.widgetType.alerts_ticker, displayName: 'Alerts Ticker' },
    { id: '5', dashType: this.widgetType.audits_for_asset_type, displayName: 'Answer Count for Asset Type (Donut)' },//'Audits For Asset Type'},
    { id: '6', dashType: this.widgetType.audits_per_week_for_asset_type, displayName: 'Answer Count Per Time Period For Asset Type' }
    ,
    { id: '7', dashType: this.widgetType.audit_count_per_time_period_for_asset_type, displayName: 'Audit Count Per Time Period For Asset Type' }
    ,
    { id: '8', dashType: this.widgetType.total_audit_score_count_for_asset_type, displayName: 'Total Audit Score Count For Asset Type' }
  ];

  /**
   * @description
   * A constant object for switching between the setting page and showing page. 
   */
  public flipState = { "Backward": "flipBackward", "Forward": "flipForward" };
  /**
  * @description
  * the first path of URL in the broswer.
  */
  public AJAX_URL_PATH1: string;
  /**
  * @description
  * The widgets shown on the dashboard page.
  */
  public Widgets: WidgetModel[];
  private AngularVersion: string;

  constructor(
    //@Inject(APP_BASE_HREF) base_href:string,
    @Inject(DOCUMENT) document: any,
    platformLocation: PlatformLocation,
    public agsi_http: Http,
    public logger: LoggerService
  ) {
    this.Widgets=[];
    this.AngularVersion = `Angular! v${VERSION.full} ${APP_BASE_HREF} <br>${platformLocation}`;//+base_href;

    // console.log(platformLocation.location);
    // console.log(platformLocation.location.href);
    // console.log(platformLocation.location.origin);

    console.log(this.AngularVersion);
    console.log(document.location.href);
    console.log(document.location);
    // console.log(base_href);
    this.AJAX_URL_PATH1 = this.getAJAXURLPATH1();
  }
  public getWidgets(): WidgetModel[] {
    // public getWidgets():Observable<WidgetModel[]>{
    this.initailWidgetModel();
    return this.Widgets;
  }
  public getUserConfig(): Observable<WidgetModel[]> {

    let widgetUrl = this.AJAX_URL_PATH1 + '/getUserConfig';// '/api/getUserConfig';  // URL to web API

    // widgetUrl = "/" + credentials.host + widgetUrl
    let log = this.logger;
    let errMsg;


    if (dev) {
      // headers.append('Cookie', this.ANGULARTESTID);
    }
    let SE_USERID = this.getSE_USERID();
    let p = new URLSearchParams();
    p.set('jsonData', JSON.stringify({ 'SE_USERID': SE_USERID }));
    // p.set('SE_USERID',userid);
    var headers = new Headers();
    // headers.append('Content-Type', 'application/json');
    headers.append('Content-Type', 'application/json; charset=utf-8');
    //  headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    //  headers.append('Content-Type', 'text/plain; charset=utf-8');

    // headers.append( "cache-control", "no-cache" );
    let jsonData=  JSON.stringify({SE_USERID:SE_USERID});
    let data = encodeURIComponent('jsonData='+jsonData);//encodeURI('jsonData='+jsonData);//encodeURIComponent('jsonData={"SE_USERID":"aliang"}');
    let options = new RequestOptions({
      // search:p
      //  ,params:p
      //  ,
      withCredentials: true
      //  ,body: JSON.stringify(data)
    });
    options.headers = headers;
    // options.method=RequestMethod.Post;
    return this.agsi_http.post(widgetUrl
      // ,JSON.stringify(data)
      // ,{}
      , data
      , options)
      // return this.agsi_http.post(widgetUrl,
      // {},
      //     //  {jsonData : JSON.stringify({SE_USERID:userid})},
      //  options)
      .map(res => {
        try {
          let body: any = res.json();
          // result:-2999
          // error:2017-05-08 10:42:51.587-0400 2999 - Session Expired. Please login again.
          if (body.error) {
            errMsg = body.result + " : " + body.error;
            //result.push(body);
            log.consoleError(errMsg);
            throw body.result;//"-2999";
          }
          // else{
          //   result.push(body || [{}]);
          // }
        }
        catch (e) {
          //conversion to Error type
          // this.logger.consoleError((<Error>e).message);

          log.consoleLog(errMsg);
          throw errMsg;
        }

        let o = res.json();
        let Widgets = JSON.parse(o.DASH_USERCONFIG);
        this.saveDataOfWidgets();
        return Widgets || {};
      })
      // .map(this.extractData)
      .catch(this.handleError);
  }
  /**
   * @description Match handleEventsOfNav() in dashboard-ac.component.
   */
  public handleEventsOfNav(event: any) {
    if (event === "saveDashboardConfig") {
      this.saveDashboardConfig();
    }
    else if (event === "resetDashboardConfig") {
      this.resetDashboard().subscribe(
        ws => {

          this.Widgets = ws;
          this.logger.consoleLog("Widgets in panels.component :" + this.Widgets);
        },
        error => {
          let errorMessage = <any>error;
          this.logger.consoleLog("errorMessage in panels.component :" + errorMessage);
        }
      );
    }
    else if (event === "addDashWidget") {
      // this.logger.log(this.eventNav);
      // setTimeout(()=>{});
      this.addDashWidget();
      //this.oldEventNav=this.eventNav;
      // setTimeout(()=>{
      //   this.eventNav="";
      //     });
      //alert("2"+this.eventNav);
      //this.logger.log(this.eventNav);
      //  this.cdRef.detectChanges();
    }
  }

  private initailWidgetModel() {
    this.Widgets = [
      {
        "widgetIndex": "1", "name": "name1",
        "dashtype": this.dashBoardWidgetTypes[0].dashType,//"default_widget": "default_widget"
        "assetitemnumber": "1"

      },
      {
        "widgetIndex": "2", "name": "name1",
        "dashtype": this.dashBoardWidgetTypes[1].dashType,
        "assetitemnumber": "2"

      }
      ,
      { "assettype": "Restaurant", "ASSETTYPE_SYSID": "2002", "asset": "1000", "flipState": "flipBackward", "widgetIndex": 0, "$$hashKey": "object: 48" },
      { "assettype": "Restaurant", "ASSETTYPE_SYSID": "2002", "asset": "1001", "flipState": "flipBackward", "widgetIndex": 1, "$$hashKey": "object:49" },
      { "assettype": "Restaurant", "ASSETTYPE_SYSID": "2002", "asset": "1002", "flipState": "flipBackward", "widgetIndex": 2, "$$hashKey": "object:50" },
      { "assettype": "Restaurant", "ASSETTYPE_SYSID": "2002", "asset": "1003", "flipState": "flipBackward", "widgetIndex": 3, "$$hashKey": "object:51" },
      { "assettype": "Restaurant", "ASSETTYPE_SYSID": "2002", "asset": "1004", "flipState": "flipBackward", "widgetIndex": 4, "$$hashKey": "object:52" },
      { "assettype": "Restaurant", "ASSETTYPE_SYSID": "2002", "asset": "1005", "flipState": "flipBackward", "widgetIndex": 5, "$$hashKey": "object:53" },
      { "assettypesix": "Restaurant", "donutone": "1003", "flipState": "flipBackward", "widgetIndex": 6, "$$hashKey": "object:54" },

      { "assettypesix": "Restaurant", "barone": "1002", "flipState": "flipBackward", "widgetIndex": 7, "$$hashKey": "object:55" },

      {
        "dashtype": "alerts-ticker", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "", "ASSET_TYPE_SYSID": "", "flipState": "flipForward", "widgetIndex": 11,
        "ASSET_CONTROL_SYSID": "0", "$$hashKey": "object:2661", "beginDate": "Thu Jan 01 2015 11:24:55 GMT-0500 (Eastern Standard Time)",
        "endDate": "Tue Feb 28 2017 11:24:55 GMT-0500 (Eastern Standard Time)", "pageSize": 7, "dynamicEndD": "YES"
      },
      {
        "dashtype": "t6", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "POLE", "ASSET_TYPE_SYSID": "", "flipState": "flipBackward", "ASSET_CONTROL_SYSID": "0",
        "widgetIndex": "61b92fd8-7e40-4acf-b6d7-ca10e10a6982", "divID": "61b92fd8-7e40-4acf-b6d7-ca10e10a6982", "assettypeID": "8", "score": "A_SCORE_COUNT", "stxt": "A", "templateID": "1",
        "templateName": "Wood Pole", "startdate": "Fri Aug 01 2014 00:00:00 GMT-0400 (Eastern Daylight Time)", "enddate": "Thu Feb 23 2017 11:03:17 GMT-0500 (Eastern Standard Time)",
        "$$hashKey": "object:3333", "dynamicEndD": "YES"
      },
      {
        "dashtype": "t6", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "POLE", "ASSET_TYPE_SYSID": "", "flipState": "flipBackward", "ASSET_CONTROL_SYSID": "0",
        "widgetIndex": "f4b08eb3-dfd2-4bbf-8fea-6e7d92832c78", "divID": "f4b08eb3-dfd2-4bbf-8fea-6e7d92832c78", "assettypeID": "8", "score": "B_SCORE_COUNT", "stxt": "B", "templateID": "1",
        "templateName": "Wood Pole", "startdate": "Fri Aug 01 2014 00:00:00 GMT-0400 (Eastern Daylight Time)", "enddate": "Thu Feb 23 2017 11:03:47 GMT-0500 (Eastern Standard Time)",
        "$$hashKey": "object:3460", "dynamicEndD": "YES"
      },
      {
        "dashtype": "t6", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "POLE", "ASSET_TYPE_SYSID": "",
        "flipState": "flipBackward", "ASSET_CONTROL_SYSID": "0", "widgetIndex": "a352cd41-740d-4334-9e79-f846bb4a56cb", "divID": "a352cd41-740d-4334-9e79-f846bb4a56cb", "assettypeID": "8",
        "score": "C_SCORE_COUNT", "stxt": "C", "templateID": "1", "templateName": "Wood Pole", "startdate": "Fri Aug 01 2014 00:00:00 GMT-0400 (Eastern Daylight Time)",
        "enddate": "Thu Feb 23 2017 11:04:27 GMT-0500 (Eastern Standard Time)", "$$hashKey": "object:3968", "dynamicEndD": "YES"
      },
      {
        "dashtype": "t6", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "POLE", "ASSET_TYPE_SYSID": "", "flipState": "flipBackward", "ASSET_CONTROL_SYSID": "0",
        "widgetIndex": "890a3524-b3e4-4523-8f21-6b2543802ac9", "divID": "890a3524-b3e4-4523-8f21-6b2543802ac9", "assettypeID": "8", "score": "D_SCORE_COUNT", "stxt": "D", "templateID": "1",
        "templateName": "Wood Pole", "startdate": "Fri Aug 01 2014 00:00:00 GMT-0400 (Eastern Daylight Time)", "enddate": "Thu Feb 23 2017 11:04:54 GMT-0500 (Eastern Standard Time)",
        "$$hashKey": "object:3841", "dynamicEndD": "YES"
      },
      {
        "dashtype": "t6", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "POLE", "ASSET_TYPE_SYSID": "", "flipState": "flipBackward", "ASSET_CONTROL_SYSID": "0",
        "widgetIndex": "ec3868f3-752a-4f7b-b3f4-f3c01c2737d9", "divID": "ec3868f3-752a-4f7b-b3f4-f3c01c2737d9", "assettypeID": "8", "score": "E_SCORE_COUNT", "stxt": "E", "templateID": "1",
        "templateName": "Wood Pole", "startdate": "Fri Aug 01 2014 00:00:00 GMT-0400 (Eastern Daylight Time)", "enddate": "Thu Feb 23 2017 11:02:14 GMT-0500 (Eastern Standard Time)",
        "$$hashKey": "object:3714"
      },
      {
        "dashtype": "t7", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "POLE", "ASSET_TYPE_SYSID": "8", "flipState": "flipBackward", "ASSET_CONTROL_SYSID": "0",
        "widgetIndex": "42a58f65-cce3-4153-8f77-b8a9689ce06c", "pickenddate": "2017-02-16T05:00:00.000Z", "timebasis": "DAY", "pickstartdate": "2016-12-16T05:00:00.000Z", "charttype": "Area",
        "templateID": "1", "$$hashKey": "object:1379", "dynamicEndD": "YES"
      },
      {
        "dashtype": "audits-for-asset-type", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "SWITCH", "ASSET_TYPE_SYSID": "9", "flipState": "flipBackward",
        "ASSET_CONTROL_SYSID": "0", "widgetIndex": "ec4a0879-e3c1-49f4-8f70-4e049e200980", "D_AUDIT_QUESTION_TYPE_CODE": "10", "$$hashKey": "object:2227"
      },
      {
        "dashtype": "t7", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "PADMOUNTED TRANSFORMER", "ASSET_TYPE_SYSID": "6", "flipState": "flipBackward",
        "ASSET_CONTROL_SYSID": "0", "widgetIndex": "f047d370-a736-4cbc-9c5f-d5e34506f975", "pickenddate": "2017-02-16T05:00:00.000Z", "timebasis": "DAY",
        "pickstartdate": "2016-12-16T05:00:00.000Z", "charttype": "Line", "templateID": "3", "$$hashKey": "object:2545", "dynamicEndD": "YES"
      },
      {
        "dashtype": "audits-for-asset-type", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "PADMOUNTED TRANSFORMER", "ASSET_TYPE_SYSID": "6",
        "flipState": "flipBackward", "ASSET_CONTROL_SYSID": "0", "widgetIndex": "23b36254-996d-43b0-9b02-69cb37ad6c15", "D_AUDIT_QUESTION_TYPE_CODE": "10", "$$hashKey": "object:2439"
      },
      {
        "dashtype": "scores_for_assettype", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "POLE", "ASSET_TYPE_SYSID": "8", "flipState": "flipBackward",
        "ASSET_CONTROL_SYSID": "0", "widgetIndex": "a5dd3213-94f4-4623-85f8-9f4807c4285a", "$$hashKey": "object:1813", "AUDIT_ANSWERS_TYPE_VALUE": "A", "D_AUDIT_QUESTION_TYPE_CODE": "10"
      },
      {
        "dashtype": "scores_for_assettype", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "POLE", "ASSET_TYPE_SYSID": "8", "flipState": "flipBackward",
        "ASSET_CONTROL_SYSID": "0", "widgetIndex": "37a45099-1ebd-4273-8e3b-85f110c3f0c2", "$$hashKey": "object:1705", "AUDIT_ANSWERS_TYPE_VALUE": "B", "D_AUDIT_QUESTION_TYPE_CODE": "10"
      },
      {
        "dashtype": "scores_for_assettype", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "SWITCH", "ASSET_TYPE_SYSID": "9", "flipState": "flipBackward",
        "ASSET_CONTROL_SYSID": "0", "widgetIndex": "be970d8d-2a2a-4bda-bece-3488ea3965de", "$$hashKey": "object:1490", "AUDIT_ANSWERS_TYPE_VALUE": "B", "D_AUDIT_QUESTION_TYPE_CODE": "10"
      },
      {
        "dashtype": "scores_for_assettype", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "POLE", "ASSET_TYPE_SYSID": "8", "flipState": "flipBackward",
        "ASSET_CONTROL_SYSID": "0", "widgetIndex": "b05dbd03-62c6-4771-8b2d-3c3830426c03", "$$hashKey": "object:1597", "AUDIT_ANSWERS_TYPE_VALUE": "D", "D_AUDIT_QUESTION_TYPE_CODE": "10"
      },
      {
        "dashtype": "scores_for_assettype", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "POLE", "ASSET_TYPE_SYSID": "8", "flipState": "flipBackward",
        "ASSET_CONTROL_SYSID": "0", "widgetIndex": "631cda75-61dd-4708-b103-e0f18ed4aeef", "$$hashKey": "object:902", "AUDIT_ANSWERS_TYPE_VALUE": "E", "D_AUDIT_QUESTION_TYPE_CODE": "10"
      },
      {
        "dashtype": "audits-per-week-for-asset-type", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "", "ASSET_TYPE_SYSID": "", "flipState": "flipForward",
        "widgetIndex": "9a66462a-bfc4-4a1f-9d16-001692682dc1", "ASSET_CONTROL_SYSID": "0", "$$hashKey": "object:2749", "divID": "9a66462a-bfc4-4a1f-9d16-001692682dc1"
      },
      {
        "dashtype": "scores_for_asset", "assetID": "0", "ASSET_SYSID": "21762191", "name": "New Widget", "assettype": "", "ASSET_TYPE_SYSID": "", "flipState": "flipForward",
        "widgetIndex": "3169f470-7e3d-494a-bbc0-4a4261161570", "ASSET_CONTROL_SYSID": "1001", "$$hashKey": "object:2883", "divID": "3169f470-7e3d-494a-bbc0-4a4261161570"
      },
      {
        "dashtype": "scores_for_asset", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "", "ASSET_TYPE_SYSID": "", "flipState": "flipForward",
        "widgetIndex": "987f2b3f-1216-449c-9393-c8f12d9a5307", "ASSET_CONTROL_SYSID": "0", "$$hashKey": "object:2326", "divID": "987f2b3f-1216-449c-9393-c8f12d9a5307"
      },
      {
        "dashtype": "scores_for_assettype", "assetID": "0", "ASSET_SYSID": "0", "name": "New Widget", "assettype": "", "ASSET_TYPE_SYSID": "", "flipState": "flipForward",
        "widgetIndex": "787acd2c-df52-4a44-9753-ffef2d032d75", "ASSET_CONTROL_SYSID": "0", "$$hashKey": "object:2362", "divID": "787acd2c-df52-4a44-9753-ffef2d032d75"
      }

    ];
  }

  /**
* @function Save Dashboard for getting an event
* @description
* Save dashboard config message into the server when Click the button "Save Dashboard".
*/
  private saveDashboardConfig(): void {
    this.setUserConfig()
      .map(res => res.json())
      .subscribe(
      data => {
        //  this.logger.consoleLog("Widgets in panels.component :"+this.Widgets);
        if (data && data.result > 0 && !data.error) {//data.result>0){
          window.alert("$scope.localLang.Save_Confirmed,$scope.localLang.Save");
        } else {
          if (data.result && data.result == -2998) {
            window.alert("-2998 " + "$scope.localLang.SavingFailed"
              + "$scope.localLang.ConnectionTimedOut,$scope.localLang.Warning");
          } else if (data.result && data.result == -2999) {
            window.alert("-2999 " + "$scope.localLang.SavingFailed"
              + "$scope.localLang.SessionTimoutText,$scope.localLang.Warning");
            // $location.path('/');
          }
          else if (data.result && data.result == -1003) {
            window.alert("-1003 " + "$scope.localLang.SavingFailed"
              + "$scope.localLang.SessionTimoutText,$scope.localLang.Warning" + data.error);
            // $location.path('/');
          }
          else {
            window.alert("Error: " + "$scope.localLang.SavingFailed" + ",Please try again. " + " " + data.result + " " + data.error +
              ",$scope.localLang.Warning");

          }
        }


      },
      error => {
        let errorMessage = <any>error;
        this.logger.consoleLog("agsi---errorMessage of saveDashboardConfig() in dashboard.service :" + errorMessage);
      }
      );
  }
  public setUserConfig(): Observable<Response> {
    let widgetUrl = this.AJAX_URL_PATH1 + '/setUserConfig';
    let log = this.logger;
    let errMsg;
    if (dev) {
      // headers.append('Cookie', this.ANGULARTESTID);
    }
    let userid = this.getSE_USERID();
    var headers = new Headers();
    // headers.append('Content-Type', 'application/json');
    headers.append('Content-Type', 'application/json; charset=utf-8');
    //  headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    //  headers.append('Content-Type', 'text/plain; charset=utf-8');
    //  headers.append( "Cache-Control", "no-cache" );
    // X-Requested-With:XMLHttpRequest
    headers.append("X-Requested-With", "XMLHttpRequest");
    let DASH_USERCONFIG = JSON.stringify(this.Widgets);
    let jsonData = JSON.stringify({ "SE_USERID": userid, "DASH_USERCONFIG": DASH_USERCONFIG });
    let data = encodeURI('jsonData=' + jsonData);//encodeURIComponent('jsonData={"SE_USERID":"aliang"}');
    let options = new RequestOptions({
      // search:p
      //  ,params:p
      //  ,
      withCredentials: true
      //  ,body: JSON.stringify(data)
    });
    options.headers = headers;
    // options.method=RequestMethod.Post;
    return this.agsi_http.post(widgetUrl
      , data
      , options)
      .catch(this.handleError);
  }
  /**
   * @function Reset Dashboard
   * @description
   * Reset dashboard from the server when Click the button "Reset Dashboard".
   */
  private resetDashboard(): Observable<WidgetModel[]> {
    // window.alert("resetDashboardConfig of resetDashboard in dashboard.service");
    // this.logger.log('-- resetDashboardConfig -- in dashboard.service');
    // this.logger.tick();

    let widgetUrl = this.AJAX_URL_PATH1 + '/resetUserDashboardPriv';
    let log = this.logger;
    let errMsg;
    if (dev) {
      // headers.append('Cookie', this.ANGULARTESTID);
    }
    let userid = this.getSE_USERID();
    let p = new URLSearchParams();
    p.set('jsonData', JSON.stringify({ 'SE_USERID': userid }));
    // p.set('SE_USERID',userid);
    var headers = new Headers();
    // headers.append('Content-Type', 'application/json');
    headers.append('Content-Type', 'application/json; charset=utf-8');
    //  headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    //  headers.append('Content-Type', 'text/plain; charset=utf-8');
    // headers.append( "cache-control", "no-cache" );  
    let data = encodeURI('jsonData={"SE_USERID":"' + userid + '"}');//encodeURIComponent('jsonData={"SE_USERID":"aliang"}');
    let options = new RequestOptions({
      // search:p
      //  ,params:p
      //  ,
      withCredentials: true
      //  ,body: JSON.stringify(data)
    });
    options.headers = headers;
    // options.method=RequestMethod.Post;
    return this.agsi_http.post(widgetUrl
      // ,JSON.stringify(data)
      // ,{}
      , data
      , options)
      // return this.agsi_http.post(widgetUrl,
      // {},
      //     //  {jsonData : JSON.stringify({SE_USERID:userid})},
      //  options)
      .map(res => {
        try {
          let body: any = res.json();
          // result:-2999
          // error:2017-05-08 10:42:51.587-0400 2999 - Session Expired. Please login again.
          if (body.error) {
            errMsg = body.result + " : " + body.error;
            //result.push(body);
            log.consoleError(errMsg);
            throw body.result;//"-2999";
          }
          // else{
          //   result.push(body || [{}]);
          // }
        }
        catch (e) {
          //conversion to Error type
          // this.logger.consoleError((<Error>e).message);

          log.consoleLog(errMsg);
          throw errMsg;
        }

        let o = res.json();
        let Widgets = JSON.parse(o.DASH_USERCONFIG);
        this.saveDataOfWidgets();
        return Widgets || {};
      })
      // .map(this.extractData)
      .catch(this.handleError);
  }

  /**
   * remove a widget with widget index from the panel page.
   * @param widgetIndex It is UUID of a widget.
   */
  public removeWidgetOnPage(widgetIndex: string | number): void {
    // this.Widgets.splice();
    this.Widgets.splice(_.indexOf(this.Widgets, _.findWhere(this.Widgets, { widgetIndex: widgetIndex })), 1);
    this.saveDataOfWidgets();

  }
  /**
 * @function add a Dashboard Widget 
 * @description
 * add a widget showing page into the Panels page inside the browser when click the button "Add Widget".
 */
  private addDashWidget(): void {
    // this.eventDbdNav.emit("addDashWidget");
    this.logger.log('-- addDashWidget in panels--');
    // this.logger.tick();
    var NewWidget: WidgetModel;
    NewWidget = Object.assign({});
    NewWidget.widgetIndex = this.generateUUID();
    NewWidget.assetitemnumber = NewWidget.widgetIndex;
    NewWidget.dashtype = this.dashBoardWidgetTypes[0].dashType,//"default_widget": "default_widget"
      this.Widgets.push(NewWidget);
    this.saveDataOfWidgets();


  }
  /**
   * @description save some data into the browser
   */
  public saveDataOfWidgets() {
    // sessionStorage.userConfig = this.Widgets;
    sessionStorage.setItem("userConfig", JSON.stringify(this.Widgets));
    // localStorage.setItem("userConfig",JSON.stringify(this.Widgets));
    // sessionStorage.setItem("userConfig", JSON.stringify(this.Widgets)); //dataDashboardFactory.serializeJSONConfig($scope.userConfig);
  }

  /**
   * @description save user information into the browser
   */
  public savaDataOfUserLoggedin(user: any) {
    // sessionStorage.setItem("userConfig", JSON.stringify(this.Widgets)); 
    localStorage.setItem("currentUser", JSON.stringify(user));
  }
  private generateUUID(): string {
    var d = new Date().getTime();
    if (window.performance && typeof window.performance.now === "function") {
      d += performance.now();; //use high-precision timer if available
    }
    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      var r = (d + Math.random() * 16) % 16 | 0;
      d = Math.floor(d / 16);
      return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
    return uuid;
  };
  // schedules a view refresh to ensure display catches up
  tick() { this.tick_then(() => { }); }
  tick_then(fn: () => any) { setTimeout(fn, 0); }

  ASSET_LAYERS: AssetLayer[] = [{ "ASSET_CONTROL_SYSID": 0, "ASSET_LAYER_NAME": "No ASSET LAYER" }];

  /**
   * @description Retrieve AssetLayers in the scores-for-asset-widget 
   */
  getAssetControlForDashboard(): Observable<AssetLayer[]> {
    //http://192.168.10.116:8080/go360AuditandCompliance3/getAssetControlForDashboard

    // let widgetUrl =credentials.pathRewrite+'/getAssetControlForDashboard';// '/api/getAssetControlForDashboard';  // URL to web API
    let widgetUrl = this.AJAX_URL_PATH1 + '/getAssetControlForDashboard';// '/api/getAssetControlForDashboard';  // URL to web API

    // widgetUrl = "/" + credentials.host + widgetUrl
    let log = this.logger;
    let errMsg;

    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    if (dev) {
      // headers.append('Cookie', this.ANGULARTESTID);
    }
    return this.agsi_http.get(widgetUrl, { headers: headers, withCredentials: true, })
      .map(res => {
        console.log(res.json());
        try {
          let body: any = res.json();
          // result:-2999
          // error:2017-05-08 10:42:51.587-0400 2999 - Session Expired. Please login again.
          if (body.error) {
            errMsg = body.result + " : " + body.error;
            //result.push(body);
            log.consoleError(errMsg);
            throw body.result;//"-2999";
          }
          // else{
          //   result.push(body || [{}]);
          // }
        }
        catch (e) {
          //conversion to Error type
          // this.logger.consoleError((<Error>e).message);

          log.consoleLog(errMsg);
          throw errMsg;
        }


        return res.json();
      })
      // .map(this.extractData)
      .catch(this.handleError);

  }
  // Promise version
  // getAssetControlForDashboard(): Promise<AssetLayer[]> {
  //   return Promise.resolve(this.ASSET_LAYERS);
  // }  
  private extractData(res: Response) {
    let body = res.json();
    return body || {};
  }
  // Promise version
  // getAssetControlForDashboard(): Promise<AssetLayer[]> {
  //   return Promise.resolve(this.ASSET_LAYERS);
  // }
  private extractData1(res: Response, log: LoggerService) {
    let errMsg: string;
    let body = res.json();
    this.logger.consoleLog("body1:" + body);
    try {

      // result:-2999
      // error:2017-05-08 10:42:51.587-0400 2999 - Session Expired. Please login again.
      if (body.error) {
        errMsg = body.result + " : " + body.error;
        //result.push(body);
        log.consoleError(errMsg);
        throw body.result;//"-2999";
      }
      // else{
      //   result.push(body || [{}]);
      // }
    }
    catch (e) {
      //conversion to Error type
      // this.logger.consoleError((<Error>e).message);
      log.consoleLog(errMsg);
      throw errMsg;
    }

    // return body.data || { };
    this.logger.consoleLog("body2:" + body);
    this.logger.consoleLog("body3.data:" + body.data);
    this.logger.consoleLog("body4[0]:" + body[0].ASSET_CONTROL_SYSID + body[0].ASSET_LAYER_NAME);
    return body as AssetLayer;
  }
  public handleError(error: Response | any) {
    // In a real world app, you might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    // console.error(errMsg);
    return Observable.throw(errMsg);
  }
  private handleError1(error: Response | any, log: LoggerService) {
    // In a real world app, you might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    // console.error(errMsg);
    log.consoleError(errMsg);
    return Observable.throw(errMsg);
  }

  public getAJAXURLPATH1(): string {
    let p = document.location.pathname;//"/go360AuditandCompliance3/dashboard/" or "/"
    let n = p.indexOf('/');
    if (n != -1) {
      p = p.substring(n + 1);
    }
    n = p.indexOf('/');
    if (n != -1) {
      p = p.substring(0, n);
    }
    let u: string = document.location.origin + '/' + p;
    if (dev) {
      u = "http://192.168.10.116:8080/Angular2AuditCompliance"; //for develpoment environment
    }
    this.logger.consoleLog(u);

    return u;
  }
  /**
   * 
   */
  authenticate(username: string, password: string) {
    var body = `{"username":"${username}","password":"${password}"}`;
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    let options = new RequestOptions({ headers: headers, withCredentials: true });

    return this.agsi_http
      .post('http://demo...', body, options)
      .subscribe(
      (response: Response) => {
        //  this.doSomething(response);
      }, (err) => {
        console.log('Error: ' + err);
      });
  }
  // setJsessionID(){
  //   let myHeader = new Headers();
  // myHeader.append('Cookies', 'JSESSIONID=<jsessionid>');

  // this.agsi_http.get(endpoint, {headers: myHeader, withCredentials: true}).map(res => res.json()).subscribe(
  //   jwt => {
  //     ...
  //   },err => console.log(err));
  // }
  public getSE_USERID(): string {
    return "aliang";//"utility_demo";
  }
  public loginTest() {
    if (dev) {
      this.loginTest_temp();
    }
  }
  public ANGULARTESTID: string;
  /**
 * @description
 * for login test
 */
  public loginTest_temp() {

    var url = "http://192.168.10.116:8080/go360AuditandCompliance3/servlet/LoginBean?action=loginVerify&username=utility_demo&password=demo1234&MachineID=chrome%2058.0.3029.81&OSType=windows-7&APPName=GO360%20Audit%20Compliance%20Utility";
    var urlsCORS = 'http://192.168.10.116:8080/Angular2AuditCompliance/servlet/LoginBean?action=loginVerify&username=utility_demo&password=demo1234&MachineID=chrome%2058.0.3029.81&OSType=windows-7&APPName=GO360%20Audit%20Compliance%20Utility';

    // var urlProxy = credentials.pathRewrite+"/servlet/LoginBean?action=loginVerify&username=utility_demo&password=demo1234&MachineID=chrome%2058.0.3029.81&OSType=windows-7&APPName=GO360%20Audit%20Compliance%20Utility";
    var urlProxy = this.AJAX_URL_PATH1 + "/servlet/LoginBean?action=loginVerify&username=utility_demo&password=demo1234&MachineID=chrome%2058.0.3029.81&OSType=windows-7&APPName=GO360%20Audit%20Compliance%20Utility";

    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    let options = new RequestOptions({ headers: headers, withCredentials: true });

    //Use Promise
    //     this.agsi_http
    //     .get(urlProxy  ,  {withCredentials : true })
    //   .toPromise()
    //   .then( (response: Response)=>{
    //      console.log("response: "+response);
    //      let headers = response.headers;
    //      console.log("headers: "+headers);
    //      console.log("res.get(set-cookie)"+response.headers.get("set-cookie"));
    // console.log("res.get(Cookie)"+response.headers.get("Cookie"));
    //  console.log("res.getAll(set-cookie)"+response.headers.getAll("set-cookie"));
    //  console.log("res.get(cookies)"+response.headers.get("cookies"));



    //   })
    //   .catch(err=>console.log(err));

    this.agsi_http
      .get(urlProxy, { withCredentials: true }
      )
      .map(
      (res) => {
        console.log("response1: " + res);
        return res;
      }
      )
      .subscribe(
      (response: Response) => {
        //  this.doSomething(response);
        console.log("response2: " + response.headers);
        let o = response.json();
        if (dev) {
          //  this.ANGULARTESTID = o.angularid;
        }
      }, (err) => {
        console.log('Error: ' + err);  //err==0 - {"isTrusted":true} means Server down
      });
    // .map((response) => {
    //   var user = response.json();
    //   // console.log("user object>"+user.result);
    //   // if(user && user.token){
    //   localStorage.setItem("currentUser", JSON.stringify(user));
    //   //   // this.subject.next(Object.assign({},user));
    //   // }
    //   // return response;
    //   return user;
    // })
    // .subscribe(
    // (data:Response) => {
    //   // console.log("login success>"+data.methodName +" sessionId: "+ data.sessionid);
    //    console.log("data:"+data);
    // },
    // error => {
    //   // console.error(error);
    // }
    // );
    //  this.http.get(url).map(res=> {
    //    this.test=res.json();
    //    this.logger.log(this.test);
    //   })
    this.logger.consoleLog("test1:" + document.cookie);

    this.logger.consoleLog("test2:" + this.getCookie("JSESSIONID"));

  }

  public getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
  }

  public getDebug():boolean{
    let r:boolean=false;
    let s:string =document.location.search;
    var n = s.indexOf("debug");
    if(n!=-1){
      var str = s.substring(n);
      if(str=='debug=1'){ r=true;}
    }
    // let s:string[] = document.location.search.split('=');
    return r;
  }

}
