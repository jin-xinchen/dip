import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditCountPerTimePeriodForAssetTypeComponent } from './audit-count-per-time-period-for-asset-type.component';

describe('AuditCountPerTimePeriodForAssetTypeComponent', () => {
  let component: AuditCountPerTimePeriodForAssetTypeComponent;
  let fixture: ComponentFixture<AuditCountPerTimePeriodForAssetTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditCountPerTimePeriodForAssetTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditCountPerTimePeriodForAssetTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
