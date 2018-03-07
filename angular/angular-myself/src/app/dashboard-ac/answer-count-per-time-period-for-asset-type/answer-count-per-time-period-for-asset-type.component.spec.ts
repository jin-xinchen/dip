import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerCountPerTimePeriodForAssetTypeComponent } from './answer-count-per-time-period-for-asset-type.component';

describe('AnswerCountPerTimePeriodForAssetTypeComponent', () => {
  let component: AnswerCountPerTimePeriodForAssetTypeComponent;
  let fixture: ComponentFixture<AnswerCountPerTimePeriodForAssetTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnswerCountPerTimePeriodForAssetTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnswerCountPerTimePeriodForAssetTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
