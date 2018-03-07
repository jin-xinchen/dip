import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';

import { DashboardAcRoutingModule } from './dashboard-ac-routing.module';
import { DashboardACComponent } from './dashboard-ac/dashboard-ac.component';
import { DashboardNavComponent } from './dashboard-nav/dashboard-nav.component';
import { PanelsComponent } from './panels/panels.component';

//Wijmo
// import * as wjcCore from 'wijmo/wijmo';
import * as wjCore from 'wijmo/wijmo';
// import * as wjcGrid from 'wijmo/wijmo.grid';
import * as wjGrid from 'wijmo/wijmo.grid';
import * as wjInput from 'wijmo/wijmo.angular2.input';
// import * as wjcGridFilter from 'wijmo/wijmo.grid.filter';
import * as wjFilter from 'wijmo/wijmo.angular2.grid.filter';




import { WjGridModule, WjFlexGrid } from 'wijmo/wijmo.angular2.grid';
import { WjGridFilterModule } from 'wijmo/wijmo.angular2.grid.filter';
import { WjInputModule } from 'wijmo/wijmo.angular2.input';


//dashboard
import { LoggerService }  from './share/logger.service';
import { DashboardService }  from './share/dashboard.service';
import { DefaultDashWidgetComponent } from './default-dash-widget/default-dash-widget.component';
import { ScoresForAssetWidgetComponent } from './scores-for-asset-widget/scores-for-asset-widget.component';
import { AnswerCountForAssetTypeComponent } from './answer-count-for-asset-type/answer-count-for-asset-type.component';
import { AlertsTickerComponent } from './alerts-ticker/alerts-ticker.component';
import { AnswerCountForAssetTypeDonutComponent } from './answer-count-for-asset-type-donut/answer-count-for-asset-type-donut.component';
import { AnswerCountPerTimePeriodForAssetTypeComponent } from './answer-count-per-time-period-for-asset-type/answer-count-per-time-period-for-asset-type.component';
import { AuditCountPerTimePeriodForAssetTypeComponent } from './audit-count-per-time-period-for-asset-type/audit-count-per-time-period-for-asset-type.component';
import { TotalAuditScoreCountForAssetTypeComponent } from './total-audit-score-count-for-asset-type/total-audit-score-count-for-asset-type.component';


@NgModule({
  
  imports: [
    CommonModule,FormsModule,HttpModule,JsonpModule,
    //dashboard
    DashboardAcRoutingModule, 
    //wijmo
    WjGridModule, WjGridFilterModule,WjInputModule
  ],
  declarations: [DashboardACComponent, DashboardNavComponent,  PanelsComponent, 
                 DefaultDashWidgetComponent, ScoresForAssetWidgetComponent, AnswerCountForAssetTypeComponent, 
                 AlertsTickerComponent, AnswerCountForAssetTypeDonutComponent, AnswerCountPerTimePeriodForAssetTypeComponent, 
                 AuditCountPerTimePeriodForAssetTypeComponent, TotalAuditScoreCountForAssetTypeComponent],
  exports:[
    DashboardACComponent, DashboardNavComponent
  ]
    ,
  providers:  [DashboardService,LoggerService]
})
/**
 * Dashboard Module
 */
export class DashboardAcModule { }
