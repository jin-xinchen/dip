import { Injectable } from '@angular/core';
import { Http, Response,RequestOptions,Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
//dashboard
import { logSwitch } from '../share/config-dev';
import { dev } from '../share/config-dev'
import { RecentScoresForAsset } from './scores-for-asset-widget-model'

import { LoggerService } from '../share/logger.service';
import { DashboardService } from '../share/dashboard.service';
@Injectable()

export class scoresForAssetService {

    constructor(
        public logger: LoggerService,
        public dbdService: DashboardService,
        public agsi_http: Http
    ) {

    }
/**
 * @description Get Recent Scores For Asset From Server
 */
    public recentScoresForAsset(paramAsset:RecentScoresForAsset): Observable<Response> {
        // return $.ajax({
		// 		type: "POST",
		// 		url: 'getRecentScoresForAsset',
		// 		data: {jsonData : JSON.stringify({ASSET_SYSID:ASSET_SYSID,ASSET_CONTROL_SYSID:ASSET_CONTROL_SYSID})},
		// 		contentType: "application/json; charset=utf-8",
		// 		headers: { "cache-control": "no-cache" },
		// 		dataType: "json"
		// 	});
        let widgetUrl = this.dbdService.AJAX_URL_PATH1 + '/getRecentScoresForAsset';
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
        let ASSET_SYSID =paramAsset.ASSET_SYSID|| "";//JSON.stringify(this.Widgets);
        let ASSET_CONTROL_SYSID=paramAsset.ASSET_CONTROL_SYSID||"";
        let jsonData = JSON.stringify({ASSET_SYSID:ASSET_SYSID,ASSET_CONTROL_SYSID:ASSET_CONTROL_SYSID});
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
