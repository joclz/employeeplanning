import {TestBed} from '@angular/core/testing';

import {MitarbeiterService} from './mitarbeiter.service';

describe('MitarbeiterServiceService', () => {
  let service: MitarbeiterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MitarbeiterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
