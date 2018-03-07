import { Component, OnInit, Input } from '@angular/core';
//import { Component, OnInit,Input,OnChanges,DoCheck,SimpleChanges,ChangeDetectionStrategy,ChangeDetectorRef } from '@angular/core';
import { WidgetModel,defaultDashWidget } from '../model/widget-model'
import { DashboardService } from '../share/dashboard.service';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

import { LoggerService } from '../share/logger.service';

@Component({
  selector: 'dboard-panels',
  templateUrl: './panels.component.html',
  styleUrls: ['./panels.component.css']
})
export class PanelsComponent implements OnInit {//, OnChanges,DoCheck  {

  @Input()
  eventNav: string;


  // Widgets: defaultDashWidget[];
  result: string;
  oldEventNav = '';

  private errorMessage:string;

  constructor(
    private http: Http, //private cdRef:ChangeDetectorRef,
    public dbdService: DashboardService,
    public logger: LoggerService
  ) {
    //test login
    this.dbdService.loginTest();
    this.retrieveWidgets();
  }

  ngOnInit() {


  }

    private retrieveWidgets() {
    //FOR TEST
    //  this.Widgets =this.dbdService.getWidgets();
    
      // this.dbdService.getUserConfig().subscribe();
   this.dbdService.getUserConfig()
      .subscribe(
      ws => {
        
         this.dbdService.Widgets =ws;
        //  this.Widgets=this.dbdService.Widgets;
         this.logger.consoleLog("Widgets in panels.component :"+this.dbdService.Widgets);
      },
      error => {
        this.errorMessage = <any>error;
        this.logger.consoleLog("errorMessage in panels.component :"+this.errorMessage);
      });

  }
  // changeLog: string[] = [];

  // ngOnChanges(changes: SimpleChanges) {
  //   for (let propName in changes) {
  //     let chng = changes[propName];
  //     let cur  = JSON.stringify(chng.currentValue);
  //     let prev = JSON.stringify(chng.previousValue);
  //     this.changeLog.push(`${propName}: currentValue = ${cur}, previousValue = ${prev}`);
  //   }
  // }

  // reset() { this.changeLog.length = 0; }

  // ngDoCheck(){
  //   // this.http.get("/api/articles.json")
  //   // .map(res=> res.json())
  //   // .subscribe((value)=>{
  //   //   this.posts=value;
  //   //  this.posts=value.filter( (value)=>{
  //   //    return value.title.toLowerCase().indexOf(this.keyword.toLowerCase())>=0;
  //   //  })

  //   // });


  // }



  //cross-origin Resource Sharing(CORS)
  // testCORS(){
  //   this.http.get("http://localhost:8080/angular4dash/getAuditAlerts").
  //   toPromise().then(r=>r.json()).then(r => this.result=r);
  // }


}
