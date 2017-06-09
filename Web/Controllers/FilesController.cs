using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Web.Entities;

namespace Web.Controllers
{

    public class FilesController : ApiController
    {
        // GET: api/Files
        public IEnumerable<FileViewModel> Get()
        {
            using (FilesContext db = new FilesContext())
            {
                var dirs = db.Files.
                    Select(x => new FileViewModel()
                    {
                        Description = x.ContentsHistory.OrderByDescending(y => y.Id).FirstOrDefault().Description,
                        Name = x.Name,
                        Accessibility = x.Accessibility.ToString(),
                        Id = x.ContentsHistory.OrderByDescending(y => y.Id).FirstOrDefault().Id,
                        Owner = x.Owner,
                        Text = x.ContentsHistory.OrderByDescending(y => y.Id).FirstOrDefault().Value
                    }).ToList();
                return dirs;
            }
        }

        // GET: api/Files/5
        public FileViewModel Get(int id)
        {
            using (FilesContext db = new FilesContext())
            {
                var content = db.Contents.Select(cnt => new FileViewModel()
                {
                    Id = cnt.Id,
                    Description = cnt.Description,
                    Accessibility = cnt.Parent.Accessibility.ToString(),
                    Name = cnt.Parent.Name,
                    Owner = cnt.Parent.Owner,
                    Text = cnt.Value
                }).FirstOrDefault(x => x.Id == id);
                return content;
            }
        }

        // POST: api/Files
        public void Post([FromBody]FileViewModel viewModel)
        {
            using (FilesContext db = new FilesContext())
            {
                var content = db.Contents.FirstOrDefault(x => x.Id == viewModel.Id);
                if (content == null)
                {
                    var file = new File()
                    {
                        Id = 0,
                        Accessibility = viewModel.Accessibility != null ? (Accessibility)Enum.Parse(typeof(Accessibility), viewModel.Accessibility) : Accessibility.Private,
                        Name = viewModel.Name,
                        Owner = "test",
                    };
                    db.Files.Add(file);
                    db.SaveChanges();
                    content = new Content()
                    {
                        Id = 0,
                        Modified = DateTime.Now,
                        Value = viewModel.Text,
                        Description = viewModel.Description,
                        Parent = file
                    };
                    db.Contents.Add(content);
                }
                else
                {
                    if (content.Value != viewModel.Text)
                    {
                        db.Contents.Add(new Content()
                        {
                            Id = 0,
                            Modified = DateTime.Now,
                            Value = viewModel.Text,
                            Description = viewModel.Description,
                            Parent = content.Parent
                        });
                    }
                }
                db.SaveChanges();
            }

        }

        // PUT: api/Files/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Files/5
        public void Delete(int id)
        {
        }
    }
}
