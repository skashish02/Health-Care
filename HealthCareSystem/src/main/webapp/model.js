function showModal(type)
{
  if (type === 'dcotors') {
    document.getElementById('supb').style.display = 'none';
  }
  else{
    document.getElementById('supb').style.display = 'inline';
 
  }
  document.querySelector('.overlay').classList.add('showoverlay')
  document.querySelector('.login-form').classList.add('showlogin-form')
  document.getElementById('login-type').value = type;

}
function closeModal()
{
  document.querySelector('.overlay').classList.remove('showoverlay')
  document.querySelector('.login-form').classList.remove('showlogin-form')
}

var c = document.querySelector('span');
c.addEventListener("click",closeModal)

