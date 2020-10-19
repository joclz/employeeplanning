import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {TableMitarbeiterComponent} from './table-mitarbeiter.component';

describe('MitarbeiterComponent', () => {
  let component: TableMitarbeiterComponent;
  let fixture: ComponentFixture<TableMitarbeiterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TableMitarbeiterComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableMitarbeiterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  //it('should create', () => {
 //   expect(component).toBeTruthy();
 // });
});
