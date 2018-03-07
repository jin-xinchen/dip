import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardACComponent } from './dashboard-ac.component';

describe('DashboardACComponent', () => {
  let component: DashboardACComponent;
  let fixture: ComponentFixture<DashboardACComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardACComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardACComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
