import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MitarbeiterSearchComponent} from './mitarbeiter-search.component';

describe('MitarbeiterSearchComponent', () => {
  let component: MitarbeiterSearchComponent;
  let fixture: ComponentFixture<MitarbeiterSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MitarbeiterSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MitarbeiterSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
