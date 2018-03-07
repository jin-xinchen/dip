import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TotalAuditScoreCountForAssetTypeComponent } from './total-audit-score-count-for-asset-type.component';

describe('TotalAuditScoreCountForAssetTypeComponent', () => {
  let component: TotalAuditScoreCountForAssetTypeComponent;
  let fixture: ComponentFixture<TotalAuditScoreCountForAssetTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TotalAuditScoreCountForAssetTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TotalAuditScoreCountForAssetTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
