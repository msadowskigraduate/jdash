import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:zoran.io/services/resource_service.dart';

@Component(
  selector: 'template-modifier',
  styleUrls: const [],
  templateUrl: 'template-modifier.html',
  directives: const [
    coreDirectives,
    formDirectives,
    MaterialInputComponent,
    MaterialButtonComponent,
    MaterialListComponent,
    MaterialListItemComponent,
    materialInputDirectives
  ],
  providers: const [materialProviders],
)
class TemplateModifier implements OnInit {
  final NewResourceService service;
  List<TemplateDataTuple> modelData;

  TemplateModifier(this.service);

  @override
  void ngOnInit() async {
    final params = service.request.templateData.join(",");
    modelData = await service.getTemplateMetadata(params);
  }

  void save() {
    if(service.request.templateTuples == null) {
      service.request.templateTuples = [];
    }
    service.request.templateTuples.addAll(modelData);
  }
}
