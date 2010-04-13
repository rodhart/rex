#!/usr/local/bin/perl

# Perl script to do automatic testing and coverage analysis of REX project

$home = "/usa/siegel/tmp";
$ant = "/usr/local/ant/bin/ant";
$out = "build.out";
$err = "build.err";

$maindir = "rex-test";

chdir($home) || die "Could not chdir $home";
if (-e $maindir) {
    system("rm -rf $maindir");
}
$cmd = "svn checkout file:///home/www/repos/cisc475/trunk $maindir";
system("$cmd >$out 2>$err");
chdir("$home/$maindir") || die "Could not chdir $home/maindir";
system("cp build-configs/build.properties-cisc475 build.properties >>$out 2>>$err");

system("$ant test");
system("cp -r junit/reports /home/www/cisc475/htdocs/junit >>$out 2>>$err");

system("$ant javadoc");
system("cp -r doc/javadoc/* /home/www/cisc475/htdocs/api >>$out 2>>$err");

system("$ant cobertura");
system("cp -r cobertura/reports /home/www/cisc475/htdocs/cobertura >>$out 2>>$err");

system("cp $out /home/www/cisc475/htdocs");
system("cp $err /home/www/cisc475/htdocs");

1;
