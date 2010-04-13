#!/usr/local/bin/perl

# Perl script to do automatic testing and coverage analysis of REX project

$home = "/usa/siegel/tmp";
$ant = "/usr/local/ant/bin/ant";

$maindir = "rex-test";

chdir($home) || die "Could not chdir $home";
if (-e $maindir) {
    system("rm -rf $maindir");
}
$cmd = "svn checkout file:///home/www/repos/cisc475/trunk $maindir";
system($cmd);
chdir("$home/$maindir") || die "Could not chdir $home/maindir";
system("cp build-configs/build.properties-cisc475 build.properties");

system("$ant test");
system("cp -r junit/reports /home/www/cisc475/htdocs/junit");

system("$ant javadoc");
system("scp -r doc/javadoc/* /home/www/cisc475/htdocs/api");

system("$ant cobertura");
system("scp -r cobertura/reports /home/www/cisc475/htdocs/cobertura");

1;
