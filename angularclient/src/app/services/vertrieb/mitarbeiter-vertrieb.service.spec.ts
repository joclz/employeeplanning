import {TestBed} from '@angular/core/testing';

import {MitarbeiterVertriebService} from './mitarbeiter-vertrieb.service';

describe('MitarbeiterVertriebService', () => {
  let service: MitarbeiterVertriebService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MitarbeiterVertriebService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
