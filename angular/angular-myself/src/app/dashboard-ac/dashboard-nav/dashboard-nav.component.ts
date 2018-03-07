import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Inject, ViewChild } from '@angular/core';
//wijmo module
import * as wjNg2Core from 'wijmo/wijmo';
import * as wjNg2Input from 'wijmo/wijmo.input';
import * as wjNg2Grid from 'wijmo/wijmo.grid';
//dashboard module
import { DashboardService } from '../share/dashboard.service';
import { LoggerService } from '../share/logger.service';


@Component({
  selector: 'dboard-dashboard-nav',
  templateUrl: './dashboard-nav.component.html',
  // styleUrls: ['../../../assets/css/bootstrap.min.css','./dashboard-nav.component.css','dialogs.css']
  styleUrls: ['./dashboard-nav.component.css', 'dialogs.css'],
  //  Directive: [wjNg2Grid.WjFlexGrid, wjNg2Grid.WjFlexGridColumn, wjNg2Grid.WjFlexGridCellTemplate, wjNg2Grid.WjTemplateCmp, wjNg2Input.WjPopup, wjNg2Input.WjInputNumber,  ….other directives ];
  // directive:[wjNg2Grid.WjFlexGrid]


})
export class DashboardNavComponent implements OnInit {

  NavLog: string[];

  @Output()
  public eventDbdNav = new EventEmitter<string>();

  @ViewChild('popupReset') popupReset: wjNg2Input.Popup;
  
  @ViewChild('popupSave') popupSave: wjNg2Input.Popup;
  constructor(
    private dbdService: DashboardService,
    private logger: LoggerService
  
  ) {
    this.NavLog = logger.logs;
  }
  showPopup() {
    this.popupReset.hideTrigger = wjNg2Input.PopupTrigger.None;
    this.popupReset.show();
  }
  hidePopup() {
    this.popupReset.hide();
  }

  // showDialog(dlg: wjNg2Input.Popup) {
  //   if (dlg) {
  //     // dlg.modal = this.modal; // Boolean
  //     dlg.hideTrigger = dlg.modal ? wjNg2Input.PopupTrigger.None : wjNg2Input.PopupTrigger.Blur;
  //     dlg.show();
  //   }
  // };

  ngOnInit() {
  }

  
  /**
   * @function invoke dashboard service or emit("saveDashboardConfig")
   * @description
   * Click the button "Save Dashboard".
   */
  public saveDashboardConfig(): void {
    // this.eventDbdNav.emit("saveDashboardConfig");
    // this.logger.log('-- saveDashboardConfig --');
    // this.logger.tick();
    this.dbdService.setUserConfig()
      .map(res => res.json())
      .subscribe(
      data => {
        //  this.logger.consoleLog("Widgets in panels.component :"+this.Widgets);
        if (data && data.result > 0 && !data.error) {//data.result>0){
          // window.alert("$scope.localLang.Save_Confirmed,$scope.localLang.Save");
          this.showPopupSave();
        } else {
          if (data.result && data.result == -2998) {
            window.alert("-2998 " +"Warning " +"Saving Failed "//+ "$scope.localLang.SavingFailed"
              +"Net Connect Exception: Connection"// "$scope.localLang.ConnectionTimedOut,$scope.localLang.Warning"
              );
          } else if (data.result && data.result == -2999) {
            window.alert("-2999 "+"Warning "  +"Saving Failed "//+ "$scope.localLang.SavingFailed"
              + "$scope.localLang.SessionTimoutText,$scope.localLang.Warning");
            // $location.path('/');
          }
          else if (data.result && data.result == -1003) {
            window.alert("-1003 "+"Warning "  +"Saving Failed "//+ "$scope.localLang.SavingFailed"
              + "Session has timed out due to excessive idle time. Please proceed to login again to continue."//"$scope.localLang.SessionTimoutText,$scope.localLang.Warning" 
              + data.error);
            // $location.path('/');
          }
          else {
            window.alert("Error: " +"Saving Failed "//+ "$scope.localLang.SavingFailed" + ",Please try again. " + " " 
            + data.result + " " + data.error 
            // +",$scope.localLang.Warning"
            );

          }
        }


      },
      error => {
        let errorMessage = <any>error;
        this.logger.consoleLog("agsi---errorMessage of saveDashboardConfig() in dashboard.service :" + errorMessage);
      }
      );

  }
  private showPopupSave():void{
    this.popupSave.show();
  }
  public hidePopupSave():void{
    this.popupSave.hide();
  }
  /**
   * @function emit("resetDashboardConfig")
   * @description
   * Click the button "Reset Dashboard".
   */
  public resetDashboardConfig(): void {
    this.showPopup();
  }
  public resetDashboard(): void {

    this.eventDbdNav.emit("resetDashboardConfig");
    this.hidePopup();
    // this.logger.log('-- resetDashboardConfig --');
    // this.logger.tick();    
  }
  /**
 * @function emit("addDashWidget")
 * @description
 * Click the button "Add Widget".
 */
  public addDashWidget(): void {
    this.eventDbdNav.emit("addDashWidget");
    // this.logger.log('-- addDashWidget --');
    // this.logger.tick();
  }


}
