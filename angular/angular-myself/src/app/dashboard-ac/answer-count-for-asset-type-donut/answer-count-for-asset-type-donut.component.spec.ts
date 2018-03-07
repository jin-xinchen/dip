import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerCountForAssetTypeDonutComponent } from './answer-count-for-asset-type-donut.component';

describe('AnswerCountForAssetTypeDonutComponent', () => {
  let component: AnswerCountForAssetTypeDonutComponent;
  let fixture: ComponentFixture<AnswerCountForAssetTypeDonutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnswerCountForAssetTypeDonutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnswerCountForAssetTypeDonutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
