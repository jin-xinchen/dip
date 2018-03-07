import { Component, OnInit,Input } from '@angular/core';

//DASHBOARD
import{DashboardService} from '../share/dashboard.service'
import{LoggerService} from '../share/logger.service'
import{scoresForAsset} from '../model/widget-model'

@Component({
  selector: 'total-audit-score-count-for-asset-type',
  templateUrl: './total-audit-score-count-for-asset-type.component.html',
  styleUrls: ['../default-dash-widget/default-dash-widget.component.css','./total-audit-score-count-for-asset-type.component.css']
})
export class TotalAuditScoreCountForAssetTypeComponent implements OnInit {

  @Input()
  defaultData:scoresForAsset;
  
  
  public flipState: string;
  constructor(
    public dbdService: DashboardService,
    private logger: LoggerService
  ) {
        this.flipState = this.dbdService.flipState.Backward;//.Forward;
   }

  ngOnInit() {
  }
    flipWidget() {
    if (this.flipState == this.dbdService.flipState.Forward) {
      // this.renderer.detachView;
      this.flipState = this.dbdService.flipState.Backward;//="flipBackward";

      this.dbdService.saveDataOfWidgets();

    } else {
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
    this.dbdService.removeWidgetOnPage(this.defaultData.widgetIndex);
    }

}
