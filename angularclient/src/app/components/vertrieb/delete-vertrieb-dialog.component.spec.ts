import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DeleteVertriebDialogComponent} from './delete-vertrieb-dialog.component';

describe('DeleteVertriebDialogComponent', () => {
  let component: DeleteVertriebDialogComponent;
  let fixture: ComponentFixture<DeleteVertriebDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteVertriebDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteVertriebDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  //it('should create', () => {
 //   expect(component).toBeTruthy();
 // });
});
