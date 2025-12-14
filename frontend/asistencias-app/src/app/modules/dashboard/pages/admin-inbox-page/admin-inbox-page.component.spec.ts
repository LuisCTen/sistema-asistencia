import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminInboxPageComponent } from './admin-inbox-page.component';

describe('AdminInboxPageComponent', () => {
  let component: AdminInboxPageComponent;
  let fixture: ComponentFixture<AdminInboxPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminInboxPageComponent]
    });
    fixture = TestBed.createComponent(AdminInboxPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
