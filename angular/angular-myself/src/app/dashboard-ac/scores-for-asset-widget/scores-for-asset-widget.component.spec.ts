import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScoresForAssetWidgetComponent } from './scores-for-asset-widget.component';

describe('ScoresForAssetWidgetComponent', () => {
  let component: ScoresForAssetWidgetComponent;
  let fixture: ComponentFixture<ScoresForAssetWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScoresForAssetWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScoresForAssetWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
