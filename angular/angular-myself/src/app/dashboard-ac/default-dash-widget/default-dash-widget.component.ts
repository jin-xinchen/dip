import { Component, OnInit, Input, EventEmitter, Renderer } from '@angular/core';

import { Http } from '@angular/http';
import 'rxjs/Rx'
//wijmo module
// import * as wjInput from 'wijmo/wijmo.angular2.input';
// import { WjInputModule } from 'wijmo/wijmo.angular2.input';
//dashboard module
import { DashboardService } from '../share/dashboard.service';
import { LoggerService } from '../share/logger.service';
import { credentials } from './credentials'
//dashboard
import { defaultDashWidget,WidgetType } from '../model/widget-model'


@Component({
  //  template: `a<wj-input-number [(value)]="amount"></wj-input-number>a`,
  selector: 'default-dash-widget',
  templateUrl: './default-dash-widget.component.html',
  styleUrls: ['./default-dash-widget.component.css']

})
export class DefaultDashWidgetComponent implements OnInit {

  @Input()
  defaultData: defaultDashWidget;
  //  @Output()
  //   public remove = new EventEmitter<string>();

  public flipState: string;
  public showing: boolean = true;
  public showingPage: string = "showing"; settingPage: string = "hide";
  DefaultDLog: string[];



  dashBoardWidgetTypes: WidgetType[];
  selectedIndex = 0; // index of item to be select
  public selectedType: string="";//http://wijmo.com/5/docs/topic/wijmo-wijmo.angular2.input.WjComboBox.Class.html#selectedItem
  // public selectedType1test:WidgetType;

    states = [
    {name: 'Arizona', abbrev: 'AZ'},
    {name: 'California', abbrev: 'CA'},
    {name: 'Colorado', abbrev: 'CO'},
    {name: 'New York', abbrev: 'NY'},
    {name: 'Pennsylvania', abbrev: 'PA'},
  ];
   


  constructor(
    private dbdService: DashboardService,
    public renderer: Renderer,
    private logger: LoggerService,
    private http: Http

  ) {
    this.flipState = this.dbdService.flipState.Backward;//.Forward;
    this.DefaultDLog = this.logger.logs;
    this.dashBoardWidgetTypes = this.dbdService.dashBoardWidgetTypes;
    // this.selectedType1test=Object.assign({},this.dashBoardWidgetTypes[0]); 
    this.selectedType = this.dbdService.dashBoardWidgetTypes[0].dashType;


  }
  // export class AppCmp {
  //     protected dataSvc: DataSvc;
  //     data: wijmo.collections.CollectionView;   
  //     selectedIndex  = 3; // index of item to be select
  //     constructor( @Inject(DataSvc) dataSvc: DataSvc) {
  //         this.dataSvc = dataSvc;
  //         this.data = new wijmo.collections.CollectionView(this.dataSvc.getCmbBxData());

  //     }

  // }
  ngOnInit() {
  }

  onSelectedIndexChanged(args: any): void {
    this.logger.log("selectedType:" + this.selectedType);
    //  console.log(args);
    //    if (args == "userinteraction") {
    //        console.log("user");
    //    } else {
    //        console.log("code");
    //    }
  }

  flipWidget() {
    if (this.flipState == this.dbdService.flipState.Forward) {
      this.showing = true;//!this.showing
      this.showingPage = "showing";
      this.settingPage = "hide";
      this.renderer.detachView;
      this.flipState = this.dbdService.flipState.Backward;//="flipBackward";

      this.defaultData.dashtype=this.selectedType;
      this.dbdService.saveDataOfWidgets();

    } else {
      this.showing = false;//!this.showing
      this.showingPage = "hide";
      this.settingPage = "showing";
      this.renderer.detachView;
      this.flipState = this.dbdService.flipState.Forward;//"flipForward";

    }
    this.logger.log("flipWidget");

  }
  test: any;
  RemoveDefault() {
    // this.remove.emit(this.defaultData.widgetIndex);
    this.dbdService.removeWidgetOnPage(this.defaultData.widgetIndex);

    this.logger.log("RemoveDefault");
    /*
    var url = "http://192.168.10.116:8080/go360AuditandCompliance3/servlet/LoginBean?action=loginVerify&username=utility_demo&password=demo1234&MachineID=chrome%2058.0.3029.81&OSType=windows-7&APPName=GO360%20Audit%20Compliance%20Utility";
    var urlsCORS = 'http://192.168.10.116:8080/Angular2AuditCompliance/servlet/LoginBean?action=loginVerify&username=utility_demo&password=demo1234&MachineID=chrome%2058.0.3029.81&OSType=windows-7&APPName=GO360%20Audit%20Compliance%20Utility';

    var urlProxy = credentials.pathRewrite+"/servlet/LoginBean?action=loginVerify&username=utility_demo&password=demo1234&MachineID=chrome%2058.0.3029.81&OSType=windows-7&APPName=GO360%20Audit%20Compliance%20Utility";
    this.http.get(urlProxy).map((response) => {
      var user = response.json();
      // console.log("user object>"+user.result);
      // if(user && user.token){
      localStorage.setItem("currentUser", JSON.stringify(user));
      //   // this.subject.next(Object.assign({},user));
      // }
      // return response;
      return user;
    })
      .subscribe(
      data => {
        // console.log("login success>"+data.methodName +" sessionId: "+ data.sessionid);
        // console.log(data);
      },
      error => {
        // console.error(error);
      }
      );
    //  this.http.get(url).map(res=> {
    //    this.test=res.json();
    //    this.logger.log(this.test);
    //   })
    this.getAllTickets();
    */
  }


  public getAllTickets() {
    var urlProxy = "/servlet/LoginBean?action=loginVerify&username=utility_demo&password=demo1234&MachineID=chrome%2058.0.3029.81&OSType=windows-7&APPName=GO360%20Audit%20Compliance%20Utility";
    // urlProxy = "/" + credentials.host + urlProxy;
    urlProxy=credentials.pathRewrite+ urlProxy;
    this.http.get(urlProxy)
      .map(res => {
        this.test = res.json();
        this.logger.log(this.test);
        return this.test;
      })
      .subscribe(
      data => {
        // console.log(">"+data.methodName +" : "+ data.sessionid);
        // console.log(data);
      },
      error => {
        // console.error(error);
      }
      );
    //  {
    //     method: 'GET',
    //     headers: new Headers([
    //         'Accept', 'application/json',
    //         'Content-Type', 'application/json'
    //     ])
    // }
    // );
  }
}
