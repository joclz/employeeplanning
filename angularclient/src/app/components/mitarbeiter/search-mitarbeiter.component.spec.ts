import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SearchMitarbeiterComponent} from './search-mitarbeiter.component';

describe('MitarbeiterSearchComponent', () => {
  let component: SearchMitarbeiterComponent;
  let fixture: ComponentFixture<SearchMitarbeiterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SearchMitarbeiterComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchMitarbeiterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
