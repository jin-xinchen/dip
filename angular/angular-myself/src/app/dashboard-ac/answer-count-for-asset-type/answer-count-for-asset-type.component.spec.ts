import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerCountForAssetTypeComponent } from './answer-count-for-asset-type.component';

describe('AnswerCountForAssetTypeComponent', () => {
  let component: AnswerCountForAssetTypeComponent;
  let fixture: ComponentFixture<AnswerCountForAssetTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnswerCountForAssetTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnswerCountForAssetTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
