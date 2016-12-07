#include <vtkActor.h>
#include <vtkCamera.h>
#include <vtkImplicitBoolean.h>
#include <vtkBooleanOperationPolyDataFilter.h>
#include <vtkSampleFunction.h>
#include <vtkSmartPointer.h>
#include <vtkProperty.h>

#include <vtkCone.h>
#include <vtkConeSource.h>
#include <vtkContourFilter.h>
#include <vtkCylinder.h>
#include <vtkCylinderSource.h>
#include <vtkPlane.h>
#include <vtkPlaneSource.h>
#include <vtkSphere.h>
#include <vtkSphereSource.h>

#include <vtkPolyData.h>
#include <vtkPolyDataMapper.h>
#include <vtkRenderer.h>
#include <vtkRenderWindow.h>
#include <vtkRenderWindowInteractor.h>

int main(int, char *[])
{
  // Base elements
  vtkSmartPointer<vtkCone> cone = vtkSmartPointer<vtkCone>::New();
  cone->SetAngle(20);

  vtkSmartPointer<vtkPlane> vertPlane = vtkSmartPointer<vtkPlane>::New();
  vertPlane->SetOrigin(.1, 0, 0);
  vertPlane->SetNormal(-1, 0, 0);

  vtkSmartPointer<vtkPlane> basePlane = vtkSmartPointer<vtkPlane>::New();
  basePlane->SetOrigin(1.2, 0, 0);
  basePlane->SetNormal(1, 0, 0);

  vtkSmartPointer<vtkSphere> iceCream = vtkSmartPointer<vtkSphere>::New();
  iceCream->SetCenter(1.333, 0, 0);
  iceCream->SetRadius(0.5);

  vtkSmartPointer<vtkSphere> bite = vtkSmartPointer<vtkSphere>::New();
  bite->SetCenter(1.5, 0, 0.5);
  bite->SetRadius(0.25);

  // Cone
  vtkSmartPointer<vtkImplicitBoolean> theCone = vtkSmartPointer<vtkImplicitBoolean>::New();
  theCone->SetOperationTypeToIntersection();
  theCone->AddFunction(cone);
  theCone->AddFunction(vertPlane);
  theCone->AddFunction(basePlane);

  vtkSmartPointer<vtkSampleFunction> theConeSample = vtkSmartPointer<vtkSampleFunction>::New();
  theConeSample->SetImplicitFunction(theCone);
  theConeSample->SetModelBounds(0, 2.5, -1.25, 1.25, -1.25, 1.25);
  theConeSample->SetSampleDimensions(120, 120, 120);
  theConeSample->ComputeNormalsOff();

  vtkSmartPointer<vtkContourFilter> theConeSurface = vtkSmartPointer<vtkContourFilter>::New();
  theConeSurface->SetInputConnection(theConeSample->GetOutputPort());
  theConeSurface->SetValue(0, 0.0);

  vtkSmartPointer<vtkPolyDataMapper> coneMapper = vtkSmartPointer<vtkPolyDataMapper>::New();
  coneMapper->SetInputConnection(theConeSurface->GetOutputPort());
  coneMapper->ScalarVisibilityOff();

  vtkSmartPointer<vtkActor> coneActor = vtkSmartPointer<vtkActor>::New();
  coneActor->SetMapper(coneMapper);
  coneActor->GetProperty()->SetColor(0.5, 0.5, 0.5);

  // Cream
  vtkSmartPointer<vtkImplicitBoolean> theCream = vtkSmartPointer<vtkImplicitBoolean>::New();
  theCream->SetOperationTypeToDifference();
  theCream->AddFunction(iceCream);
  theCream->AddFunction(bite);

  vtkSmartPointer<vtkSampleFunction> theCreamSample = vtkSmartPointer<vtkSampleFunction>::New();
  theCreamSample->SetImplicitFunction(theCream);
  theCreamSample->SetModelBounds(0, 2.5, -1.25, 1.25, -1.25, 1.25);
  theCreamSample->SetSampleDimensions(300, 300, 300);
  theCreamSample->ComputeNormalsOff();

  vtkSmartPointer<vtkContourFilter> theCreamSurface = vtkSmartPointer<vtkContourFilter>::New();
  theCreamSurface->SetInputConnection(theCreamSample->GetOutputPort());
  theCreamSurface->SetValue(0, 0.0);

  vtkSmartPointer<vtkPolyDataMapper> creamMapper = vtkSmartPointer<vtkPolyDataMapper>::New();
  creamMapper->SetInputConnection(theCreamSurface->GetOutputPort());
  creamMapper->ScalarVisibilityOff();

  vtkSmartPointer<vtkActor> creamActor = vtkSmartPointer<vtkActor>::New();
  creamActor->SetMapper(creamMapper);
  creamActor->GetProperty()->SetColor(0.8, 0., 0.4);

  // Render
  vtkSmartPointer<vtkRenderer> ren1= vtkSmartPointer<vtkRenderer>::New();
  ren1->AddActor(coneActor);
  ren1->AddActor(creamActor);
  ren1->SetBackground(0.1, 0.2, 0.4);
  vtkSmartPointer<vtkRenderWindow> renWin = vtkSmartPointer<vtkRenderWindow>::New();
  renWin->AddRenderer(ren1);
  renWin->SetSize(300, 300);
 
  vtkSmartPointer<vtkRenderWindowInteractor> renderWindowInteractor = vtkSmartPointer<vtkRenderWindowInteractor>::New();
  renderWindowInteractor->SetRenderWindow(renWin);
  renWin->Render();
  renderWindowInteractor->Start();
 
  return EXIT_SUCCESS;
}
