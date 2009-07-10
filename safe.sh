echo "saving to local user home"
tar cfv /Users/tonit/splinter.git .git
echo "saving to ipod.."
cp /Users/tonit/splinter.git "/Volumes/Toni’s iPod/toni/"
echo "done."

echo "uploading.."
#scp /Users/tonit/splinter.git tonit@osgify.com:/srv/www/vhosts/osgify.com/httpdocs
scp -r .git tonit@osgify.com:/srv/www/vhosts/osgify.com/httpdocs/splinter.git

echo "done."