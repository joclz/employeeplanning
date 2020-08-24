import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ListMitarbeiterComponent} from './list-mitarbeiter.component';

describe('ListMitarbeiterComponent', () => {
  let component: ListMitarbeiterComponent;
  let fixture: ComponentFixture<ListMitarbeiterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListMitarbeiterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListMitarbeiterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
