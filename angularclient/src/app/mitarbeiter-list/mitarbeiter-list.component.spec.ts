import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MitarbeiterListComponent} from './mitarbeiter-list.component';

describe('MitarbeiterListComponent', () => {
  let component: MitarbeiterListComponent;
  let fixture: ComponentFixture<MitarbeiterListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MitarbeiterListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MitarbeiterListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
