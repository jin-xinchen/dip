import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AlertsTickerComponent } from './alerts-ticker.component';

describe('AlertsTickerComponent', () => {
  let component: AlertsTickerComponent;
  let fixture: ComponentFixture<AlertsTickerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AlertsTickerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertsTickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
