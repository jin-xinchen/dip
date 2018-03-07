import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultDashWidgetComponent } from './default-dash-widget.component';

describe('DefaultDashWidgetComponent', () => {
  let component: DefaultDashWidgetComponent;
  let fixture: ComponentFixture<DefaultDashWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DefaultDashWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DefaultDashWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
