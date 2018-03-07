/**
 * Dashboard layout container
 */
import { Component, OnInit,Output,EventEmitter } from '@angular/core';
import { DashboardService } from '../share/dashboard.service';

@Component({
  selector: 'dboard-dashboard-ac',
  templateUrl: './dashboard-ac.component.html',
  styleUrls: ['./dashboard-ac.component.css']
})
export class DashboardACComponent implements OnInit {


    @Output()
    public eventDbdNav = new EventEmitter<string>();

    public eventNav:string;
  constructor(private dbdService:DashboardService) { }

  ngOnInit() {
  }
  public handleEventsOfNav(event:any){
      // alert("handleEventNav:"+event);
    this.eventNav=event;
    this.dbdService.handleEventsOfNav(event);
  }

}
