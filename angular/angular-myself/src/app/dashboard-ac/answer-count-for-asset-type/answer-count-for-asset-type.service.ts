import { Injectable } from '@angular/core';
import { Http, Response,RequestOptions,Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
//dashboard
import { logSwitch } from '../share/config-dev';
import { dev } from '../share/config-dev'


import { LoggerService } from '../share/logger.service';
import { DashboardService } from '../share/dashboard.service';

@Injectable()
export class answerCountForAssetTypeService {

    constructor(
        public logger: LoggerService,
        public dbdService: DashboardService,
        public agsi_http: Http
    ) {

    }
    /**
     * @description retrieve data of answer-count-for-asset-type from Server
     * @param ASSET_TYPE_SYSID,AUDIT_ANSWERS_TYPE_VALUE,D_AUDIT_QUESTION_TYPE_CODE
     */
    public recentScoresForAssetType(paramObj:any):Observable<Response>{
        // return $.ajax({
		// 		type: "POST",
		// 		url: 'recentScoresForAssetType',
		// 		data: {jsonData : JSON.stringify({ASSET_TYPE_SYSID:ASSET_TYPE_SYSID,AUDIT_ANSWERS_TYPE_VALUE:AUDIT_ANSWERS_TYPE_VALUE,D_AUDIT_QUESTION_TYPE_CODE:D_AUDIT_QUESTION_TYPE_CODE})},
		// 		contentType: "application/json; charset=utf-8",
		// 		headers: { "cache-control": "no-cache" },
		// 		dataType: "json"
		// 	});
       let widgetUrl = this.dbdService.AJAX_URL_PATH1 + '/recentScoresForAssetType';
        let log = this.logger;
        let errMsg;
        if (dev) {
            // headers.append('Cookie', this.ANGULARTESTID);
        }
        let userid = this.dbdService.getSE_USERID();
        var headers = new Headers();
        // headers.append('Content-Type', 'application/json');
        headers.append('Content-Type', 'application/json; charset=utf-8');
        //  headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
        //  headers.append('Content-Type', 'text/plain; charset=utf-8');
        //  headers.append( "Cache-Control", "no-cache" );
        // X-Requested-With:XMLHttpRequest
        headers.append("X-Requested-With", "XMLHttpRequest");
        //{ASSET_TYPE_SYSID:ASSET_TYPE_SYSID,AUDIT_ANSWERS_TYPE_VALUE:AUDIT_ANSWERS_TYPE_VALUE,D_AUDIT_QUESTION_TYPE_CODE:D_AUDIT_QUESTION_TYPE_CODE}
        let jsonData = JSON.stringify(
            {ASSET_TYPE_SYSID:paramObj.ASSET_TYPE_SYSID,AUDIT_ANSWERS_TYPE_VALUE:paramObj.AUDIT_ANSWERS_TYPE_VALUE,D_AUDIT_QUESTION_TYPE_CODE:paramObj.D_AUDIT_QUESTION_TYPE_CODE}
            );
        let data = encodeURI('jsonData=' + jsonData);//encodeURIComponent();
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
            .catch(this.dbdService.handleError);                
    }
    /**
     * @description get Answer Types of a question type in an asset type.
     */
    public getAnswerTypebyQuestionType(paramObj:any):Observable<Response>{
        let Obj = paramObj;
        // return $.ajax({
		// 		type: "POST",
		// 		url: 'getAnswerTypebyQuestionType',
		// 		data: {jsonData : JSON.stringify({ASSET_TYPE_SYSID:ASSET_TYPE_SYSID,QUESTION_TYPE_CODE:QUESTION_TYPE_CODE})},
		// 		contentType: "application/json; charset=utf-8",
		// 		headers: { "cache-control": "no-cache" },
		// 		dataType: "json"
		// 	});
        let widgetUrl = this.dbdService.AJAX_URL_PATH1 + '/getAnswerTypebyQuestionType';
        let log = this.logger;
        let errMsg;
        if (dev) {
            // headers.append('Cookie', this.ANGULARTESTID);
        }
        let userid = this.dbdService.getSE_USERID();
        var headers = new Headers();
        // headers.append('Content-Type', 'application/json');
        headers.append('Content-Type', 'application/json; charset=utf-8');
        //  headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
        //  headers.append('Content-Type', 'text/plain; charset=utf-8');
        //  headers.append( "Cache-Control", "no-cache" );
        // X-Requested-With:XMLHttpRequest
        headers.append("X-Requested-With", "XMLHttpRequest");
        // $scope.recentScoresForAssetType.ASSET_TYPE_SYSID, $scope.recentScoresForAssetType.D_AUDIT_QUESTION_TYPE_CODE
        // this.logger.consoleLog("paramObj"+JSON.stringify(Obj));

        let jsonData = JSON.stringify({ASSET_TYPE_SYSID:Obj.ASSET_TYPE_SYSID,QUESTION_TYPE_CODE:Obj.D_AUDIT_QUESTION_TYPE_CODE});
        let data = encodeURI('jsonData=' + jsonData);//encodeURIComponent();
        let options = new RequestOptions({
            // search:p
            //  ,params:p
            //  ,
            withCredentials: true
            //  ,body: JSON.stringify(data)
        });
        options.headers = headers;
        // options.method=RequestMethod.Post;
        this.logger.consoleLog("==>Obj.AUDIT_ANSWERS_TYPE_VALUE6:" + Obj.AUDIT_ANSWERS_TYPE_VALUE);
        return this.agsi_http.post(widgetUrl
            , data
            , options)
            .catch(this.dbdService.handleError);        
    }

    /**
   * @description Get Question Types of an Asset Type from Server
   */
  public getQuestionTypebyAssetType(paramAsset:any):Observable<Response>{

    //   return $.ajax({
	// 			type: "POST",
	// 			url: 'getQuestionTypebyAssetType',
	// 			data: {jsonData : JSON.stringify({ASSET_TYPE_SYSID:ASSET_TYPE_SYSID})},
	// 			contentType: "application/json; charset=utf-8",
	// 			headers: { "cache-control": "no-cache" },
	// 			dataType: "json"
	// 		});
        let widgetUrl = this.dbdService.AJAX_URL_PATH1 + '/getQuestionTypebyAssetType';
        let log = this.logger;
        let errMsg;
        if (dev) {
            // headers.append('Cookie', this.ANGULARTESTID);
        }
        let userid = this.dbdService.getSE_USERID();
        var headers = new Headers();
        // headers.append('Content-Type', 'application/json');
        headers.append('Content-Type', 'application/json; charset=utf-8');
        //  headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
        //  headers.append('Content-Type', 'text/plain; charset=utf-8');
        //  headers.append( "Cache-Control", "no-cache" );
        // X-Requested-With:XMLHttpRequest
        headers.append("X-Requested-With", "XMLHttpRequest");
        
        let jsonData = JSON.stringify({ASSET_TYPE_SYSID:paramAsset.ASSET_TYPE_SYSID});
        let data = encodeURI('jsonData=' + jsonData);//encodeURIComponent();
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
            .catch(this.dbdService.handleError);    
  }
/**
 * @description Retrieve all question types from the server.
 */
public getQuestionType(): Observable<Response>{
    // return $.ajax({
    // 		type: "POST",
    // 		url: 'getQuestionType',
    // 		data: {jsonData : JSON.stringify({})},
    // 		contentType: "application/json; charset=utf-8",
    // 		headers: { "cache-control": "no-cache" },
    // 		dataType: "json"
    // 	});
        let widgetUrl = this.dbdService.AJAX_URL_PATH1 + '/getQuestionType';
        let log = this.logger;
        let errMsg;
        if (dev) {
            // headers.append('Cookie', this.ANGULARTESTID);
        }
        let userid = this.dbdService.getSE_USERID();
        var headers = new Headers();
        // headers.append('Content-Type', 'application/json');
        headers.append('Content-Type', 'application/json; charset=utf-8');
        //  headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
        //  headers.append('Content-Type', 'text/plain; charset=utf-8');
        //  headers.append( "Cache-Control", "no-cache" );
        // X-Requested-With:XMLHttpRequest
        headers.append("X-Requested-With", "XMLHttpRequest");
        let jsonData = JSON.stringify({});
        let data = encodeURI('jsonData=' + jsonData);//encodeURIComponent();
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
            .catch(this.dbdService.handleError);    
}
/**
 * @description Get Recent Scores For Asset From Server
 */
    public getListOfAssetTypes(paramAsset:any): Observable<Response> {
// return $.ajax({
// 				type: "POST",
// 				url: 'getListOfAssetTypes',
// 				data: {jsonData : JSON.stringify({})},
// 				contentType: "application/json; charset=utf-8",
// 				headers: { "cache-control": "no-cache" },
// 				dataType: "json"
// 			});
        let widgetUrl = this.dbdService.AJAX_URL_PATH1 + '/getListOfAssetTypes';
        let log = this.logger;
        let errMsg;
        if (dev) {
            // headers.append('Cookie', this.ANGULARTESTID);
        }
        let userid = this.dbdService.getSE_USERID();
        var headers = new Headers();
        // headers.append('Content-Type', 'application/json');
        headers.append('Content-Type', 'application/json; charset=utf-8');
        //  headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
        //  headers.append('Content-Type', 'text/plain; charset=utf-8');
        //  headers.append( "Cache-Control", "no-cache" );
        // X-Requested-With:XMLHttpRequest
        headers.append("X-Requested-With", "XMLHttpRequest");
        let jsonData = JSON.stringify({});
        let data = encodeURI('jsonData=' + jsonData);//encodeURIComponent();
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
            .catch(this.dbdService.handleError);
    }

//     private handleError(error: Response | any) {
//     // In a real world app, you might use a remote logging infrastructure
//     let errMsg: string;
//     if (error instanceof Response) {
//       const body = error.json() || '';
//       const err = body.error || JSON.stringify(body);
//       errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
//     } else {
//       errMsg = error.message ? error.message : error.toString();
//     }
//     // console.error(errMsg);
//     return Observable.throw(errMsg);
//   }
}
