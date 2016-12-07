cmake_minimum_required(VERSION 2.8.5 FATAL_ERROR)

project(IceCream)

find_package(VTK REQUIRED NO_MODULE)

include(${VTK_USE_FILE})

add_executable(IceCream IceCream.cxx)

set(EXECUTABLE_FLAG MACOSX_BUNDLE)

target_link_libraries(IceCream ${VTK_LIBRARIES})
