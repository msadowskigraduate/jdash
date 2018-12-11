import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:sheets_dashboard/zoran_service.dart';

@Component(
  selector: "renderer",
  template: '<div>{{depModel.id}} {{depModel.name}} <br> {{depModel.description}}</div>',
  directives: const [
    coreDirectives
  ],
)
class DependencyRenderer implements RendersValue<LanguageDependenciesModel>{
  LanguageDependenciesModel depModel;
  @override
  set value(LanguageDependenciesModel newValue) {
    depModel = newValue;
  }
}