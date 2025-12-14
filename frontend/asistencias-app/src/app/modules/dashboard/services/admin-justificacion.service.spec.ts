import { TestBed } from '@angular/core/testing';

import { AdminJustificacionService } from './admin-justificacion.service';

describe('AdminJustificacionService', () => {
  let service: AdminJustificacionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminJustificacionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
