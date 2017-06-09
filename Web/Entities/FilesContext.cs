using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;
using System.Linq;
using System.Web;

namespace Web.Entities
{
    public class FilesContext : DbContext
    {
        public FilesContext() : base("FilesDb")
        {
        }

        public DbSet<File> Files { get; set; }
        public DbSet<Content> Contents { get; set; }
        // public DbSet<Dir> Directories { get; set; }
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Conventions.Remove<PluralizingTableNameConvention>();
        }
    }
   


}