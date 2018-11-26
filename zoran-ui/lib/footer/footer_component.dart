import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:sheets_dashboard/routing/routing.dart';
import 'package:sheets_dashboard/zoran_service.dart';

@Component(
  selector: 'footer-component',
  templateUrl: 'footer_component.html',
  directives: const [
    MaterialIconComponent
  ],
  styleUrls: const ['footer_component.scss.css'],
  providers: const [materialProviders, const ClassProvider(AppRoutes)],
)
class FooterComponent implements OnInit {

  VersionDto versionDto;

  @override
  void ngOnInit() {
    versionDto = new VersionDto("TestBranch", "1234567890",
        "localhost", "GMT+0", "0.0.1-SNAPSHOT");
  }
}