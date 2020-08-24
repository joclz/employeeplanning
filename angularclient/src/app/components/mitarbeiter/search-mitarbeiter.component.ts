import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterService} from "../../services/mitarbeiter.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

const patternId = Validators.pattern('^\\d+$');

@Component({
  selector: 'app-search-mitarbeiter',
  templateUrl: './search-mitarbeiter.component.html',
  styleUrls: ['./search-mitarbeiter.component.css']
})
export class SearchMitarbeiterComponent implements OnInit {

  searchLengthDate = new FormControl({value: '', disabled: true});
  searchChance = new FormControl({value: '', disabled: true});
  searchLengthFormGroup: FormGroup;
  searchChanceFormGroup: FormGroup;

  searchLengthId = new FormControl('', [Validators.required, patternId]);
  searchChanceId = new FormControl('', [Validators.required, patternId]);

  constructor(private route: ActivatedRoute, private router: Router, private mitarbeiterService: MitarbeiterService) {
  }

  searchLengthOnSubmit() {
    this.mitarbeiterService.getLastEndDateForMitarbeiter(this.searchLengthId.value).subscribe(result => {
      this.searchLengthDate.setValue(result.substr(0, result.indexOf('+')));
    });
  }

  searchChanceOnSubmit() {
    this.mitarbeiterService.getChanceForMitarbeiter(this.searchChanceId.value).subscribe(result => this.searchChance.setValue(result));
  }

  ngOnInit(): void {
    this.searchLengthFormGroup = new FormGroup({
      id: this.searchLengthId,
      searchLengthDate: this.searchLengthDate
    });
    this.searchChanceFormGroup = new FormGroup({
      id: this.searchChanceId,
      searchChance: this.searchChance
    });
  }
}
