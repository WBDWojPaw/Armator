function collapseAll()
{
    var tags = document.getElementsByTagName('*');
    for (var i = 0; i < tags.length; i++)
    {
        if (tags[i].className === 'item')
            tags[i].className = "itemHidden";

        if (tags[i].className === 'highlighted')
            tags[i].className = "";

    }
}


// menu script
function showDetail(itemId, linkId)
{
    collapseAll();
    linkId.className = "highlighted";

    for (i = 0; i < document.allItems.listOfItems.length; i++)
    {
        if (document.allItems.listOfItems.options[i].value === itemId)
            document.allItems.listOfItems.options[i].selected = true;
    }

    if (document.getElementById)
    {
        var docElem = document.getElementById(itemId);
        if (docElem.className === "item")
        {
            docElem.className = "itemHidden";
        }
        else
        {
            docElem.className = "item";
        }
        return true;
    }
    else
    {
        return false;
    }
}

// combo box script
function showSelected()
{
    var selIndex = document.allItems.listOfItems.selectedIndex;
    var theValue = document.allItems.listOfItems.options[ selIndex ].value;
    showDetail(theValue, this);

}

function getUrlItem()
{
    var myItem, myPosition;

    myPosition = window.location.href.indexOf("#") + 1;
    myItem = window.location.href.substr(myPosition);
    if (myPosition !== 0)
    {
        showDetail(myItem, this);
    }
    setContainerSize();
}


function setContainerSize() {

    var TopNavHeight = parseInt(document.getElementById('navTop').offsetHeight) + 70;
    var offset;
    
    if (document.getElementById("contentFullPage") !== null)
		{
				document.getElementById("contentFullPage").style.paddingTop = TopNavHeight + "px";
		}

    if (document.getElementById('bookmark') !== null)
    {
        var LeftNavWidth = parseInt(document.getElementById('bookmark').offsetWidth) + 40;
        
        if (GetWidth() > 750)
							offset = [LeftNavWidth, TopNavHeight, 		TopNavHeight];
				else  offset = [LeftNavWidth, TopNavHeight + 5, TopNavHeight + 5];
				
				if (document.getElementById("content") !== null)
				{
						document.getElementById("content").style.marginLeft = offset[0] + "px";
						document.getElementById("content").style.paddingTop = offset[1] + "px";
						document.getElementById("navLeft").style.marginTop = 	offset[2] + "px";
				}   
    }
}

function setContainerToPrint()
{
    if (document.getElementById("content") !== null)
    {
        document.getElementById("content").style.marginLeft = "0px";
        document.getElementById("content").style.paddingTop = "0px";
        document.getElementById("navLeft").style.marginTop = "0px";
    }
    if (document.getElementById("contentFullPage") !== null)
    {
        document.getElementById("contentFullPage").style.paddingTop = "70px";
        console.log("Full Page");
    }
}


function GetWidth()
{
    var x = 0;
    if (self.innerHeight)
    {
        x = self.innerWidth;
    }
    else if (document.documentElement && document.documentElement.clientHeight)
    {
        x = document.documentElement.clientWidth;
    }
    else if (document.body)
    {
        x = document.body.clientWidth;
    }
    return x;
}

window.onresize = setContainerSize;
window.onbeforeprint = setContainerToPrint;

//window.onresize=doDelayedResize;
//
//var resizeTimer = 0;
//function doDelayedResize()
//{
//    if (resizeTimer)
//        clearTimeout(resizeTimer);
//
//    resizeTimer = setTimeout(setContainerSize, 50);
//}

