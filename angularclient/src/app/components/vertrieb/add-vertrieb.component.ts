import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterVertrieb} from "../../models/mitarbeiter-vertrieb";
import {MitarbeiterVertriebService} from "../../services/mitarbeiter-vertrieb.service";

const patternNames = Validators.pattern('[a-zA-Z_äÄöÖüÜß\-]*');

@Component({
  selector: 'app-add-vertrieb',
  templateUrl: './add-vertrieb.component.html',
  styleUrls: ['./add-vertrieb.component.css']
})
export class AddVertriebComponent implements OnInit {

  @ViewChild(FormGroupDirective) formRef: FormGroupDirective;

  formGroup: FormGroup;

  id = new FormControl();
  name = new FormControl('', [Validators.required, patternNames]);
  vorname = new FormControl('', [Validators.required, patternNames]);

  constructor(private route: ActivatedRoute, private router: Router, private mitarbeiterVertriebService: MitarbeiterVertriebService) {
  }

  onSubmit() {
    let mitarbeiterVertrieb = new MitarbeiterVertrieb();
    mitarbeiterVertrieb.name = this.name.value;
    mitarbeiterVertrieb.vorname = this.vorname.value;

    this.mitarbeiterVertriebService.save(mitarbeiterVertrieb).subscribe(() =>
      this.formRef.resetForm()
    );
  }

  ngOnInit(): void {
    this.formGroup = new FormGroup({
      name: this.name,
      vorname: this.vorname
    });
  }

  ngOnDestroy(): void {
    this.formRef.resetForm();
  }

}
