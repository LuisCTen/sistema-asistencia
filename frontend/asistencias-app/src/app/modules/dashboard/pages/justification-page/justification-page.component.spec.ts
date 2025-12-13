import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JustificationPageComponent } from './justification-page.component';

describe('JustificationPageComponent', () => {
  let component: JustificationPageComponent;
  let fixture: ComponentFixture<JustificationPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JustificationPageComponent]
    });
    fixture = TestBed.createComponent(JustificationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
