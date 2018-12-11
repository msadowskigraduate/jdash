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
  final ZoranService service;

  FooterComponent(this.service);

  VersionDto versionDto;

  @override
  void ngOnInit() async {
    versionDto = await service.getVersion();
  }
}