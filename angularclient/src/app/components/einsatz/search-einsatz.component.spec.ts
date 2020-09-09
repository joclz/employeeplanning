import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SearchEinsatzComponent} from './search-einsatz.component';

describe('SearchEinsatzComponent', () => {
  let component: SearchEinsatzComponent;
  let fixture: ComponentFixture<SearchEinsatzComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SearchEinsatzComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchEinsatzComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
