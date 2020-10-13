import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {VertriebDialogData} from "../../models/vertrieb/vertrieb-dialog-data";

@Component({
  selector: 'app-delete-vertrieb-dialog',
  templateUrl: './delete-vertrieb-dialog.component.html',
  styleUrls: ['./delete-vertrieb-dialog.component.css']
})
export class DeleteVertriebDialogComponent {

  constructor(public dialogRef: MatDialogRef<DeleteVertriebDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: VertriebDialogData) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onYesClick() {
    this.dialogRef.close(this.data.id);
  }

}
