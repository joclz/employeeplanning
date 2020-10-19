import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DeleteMitarbeiterDialogComponent} from './delete-mitarbeiter-dialog.component';

describe('DeleteMitarbeiterDialogComponent', () => {
  let component: DeleteMitarbeiterDialogComponent;
  let fixture: ComponentFixture<DeleteMitarbeiterDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteMitarbeiterDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteMitarbeiterDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  //it('should create', () => {
 //   expect(component).toBeTruthy();
 // });
});
